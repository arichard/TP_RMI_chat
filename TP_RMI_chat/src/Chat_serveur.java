import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Serveur pour le chat utilisant l'API RMI
 * 
 * @author Antoine
 * 
 */
public class Chat_serveur extends UnicastRemoteObject implements
		ServeurInterface {

	protected ArrayList<String> listeClient;
	protected ArrayList<String> listeMsg;

	public Chat_serveur() throws RemoteException {
		super();
		listeClient = new ArrayList<String>();
		listeMsg = new ArrayList<String>();
	}

	/**
	 * Cette methode permet d'enregistrer un nouvel utilisateur
	 */
	public boolean enregistreUser() {
		
		Scanner scNomUtilisateur = new Scanner(System.in);
		System.out.println("\n Entrez le nom du nouvel utilisateur");
		String nomUtilisateur = scNomUtilisateur.nextLine();

		// on verifie si le nomUtilisteur est deja enregistre dans listeClient
		for (int i = 0; i < listeClient.size(); i++) {
			// on stocke l'element i de la listeClient dans nomClient
			String nomClient = (String) listeClient.get(i);
			// on verifie si le nom du client est deja enregistre
			if (nomClient.equals(nomUtilisateur)) {
				System.out.println("Cet utilisateur est deja connecte !");
				return false;
			}
		}

		System.out.println("Nouvel utilisateur connecte : " + nomUtilisateur);
		listeClient.add(nomUtilisateur);
		return true;
	}

	/**
	 * Cette methode permet d'afficher la liste des utilisateurs
	 */
	public void afficherUsers() throws RemoteException {
		System.out.println("\n Voici la liste des utilisateurs :");
		// pour chaque utilisateur dans la liste
		for (int i = 0; i < listeClient.size(); i++) {
			// on affiche son nom
			System.out.println(listeClient.get(i));
		}
	}

	/**
	 * Cette methode permet de virer un utilisateur
	 */
	public boolean kickerUser() throws RemoteException {
		Scanner scNomUtilisateur = new Scanner(System.in);
		System.out.println("\n Entrez le nom de l'utilisateur a kicker");
		String nomUtilisateur = scNomUtilisateur.nextLine();
		
		listeClient.remove(nomUtilisateur);
		System.out.println(nomUtilisateur + " s'est deconnecte.");
		return true;
	}

	/**
	 * Cette methode permet d'enregistrer les messages de la session de chat
	 */
	public boolean enregistreMsg(String nomUtilisateur, String phrase) {
		listeMsg.add(phrase);
		return true;
	}

	/**
	 * On definit cote serveur la methode de l'interface qui va permettre
	 * d'ecrire sur l'interface le message du client. Renvoie true si l'ecriture
	 * se deroule normalement.
	 * 
	 */
	public boolean serveurEcrit(String nomUtilisateur, String phrase) {

		boolean valeurDeRetour = true;
		System.out.println("Le client " + nomUtilisateur + " a envoye "
				+ phrase);
		enregistreUser();
		enregistreMsg(nomUtilisateur, phrase);

		for (int i = 0; i < listeClient.size(); ++i) {
			String nomClient = (String) listeClient.get(i);

			try {
				String name = "//" + nomClient + "/chatclient";
				ClientInterface ClientX = (ClientInterface) java.rmi.Naming
						.lookup(name);
				ClientX.notifie(nomUtilisateur, phrase);
			}

			catch (Exception e) {
				System.err.println("Erreur: impossible de contacter "
						+ nomClient);
				valeurDeRetour = false;
			}
		}

		return valeurDeRetour;
	}

	/**
	 * Ce main permet de lancer le serveur du chat.
	 */
	public static void main(String[] args) {
		try {
			// cree une instance de l'objet serveur
			Chat_serveur monServeur = new Chat_serveur();
			// enregistre l'objet cree aupres du serveur de nom
			Naming.rebind("//localhost/chat", monServeur);
			System.out.println("Le serveur de chat a ete enregistre "
					+ "dans le serveur de nom.");
		} catch (Exception e) {
			System.err.println("Exception dans Serveur : " + e.getMessage());
			e.printStackTrace();
		}
	}


}