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
	public String enregistreUser(String nomUtilisateur) {

		String result = new String();
		// on verifie si le nomUtilisteur est deja enregistre dans listeClient
		for (int i = 0; i < listeClient.size(); i++) {
			// on stocke l'element i de la listeClient dans nomClient
			String nomClient = (String) listeClient.get(i);
			// on verifie si le nom du client est deja enregistre
			if (nomClient.equals(nomUtilisateur)) {
				result = "Cet utilisateur est deja connecte !";
				System.out.println(result);
				return result;
			}
		}

		listeClient.add(nomUtilisateur);
		result = "Nouvel utilisateur connecte : " + nomUtilisateur;
		System.out.println(result);
		return result;
	}

	/**
	 * Cette methode permet d'afficher la liste des utilisateurs
	 */
	public void afficherUsers() throws RemoteException {
		System.out.println("=================================");
		System.out.println("Voici la liste des utilisateurs :");
		// pour chaque utilisateur dans la liste
		for (int i = 0; i < listeClient.size(); i++) {
			// on affiche son nom
			System.out.println(listeClient.get(i));
		}
		System.out.println("=================================");
	}

	/**
	 * Cette methode permet de virer un utilisateur
	 */
	public String kickerUser(String nomUtilisateur) throws RemoteException {
		listeClient.remove(nomUtilisateur);
		String result = nomUtilisateur + " s'est deconnecte.";
		System.out.println(result);
		return result;
	}

	/**
	 * Cette methode permet d'enregistrer les messages de la session de chat
	 */
	public boolean enregistreMsg(String nomUtilisateur, String message) {
		String messageComplet = nomUtilisateur + ":" + message;
		listeMsg.add(messageComplet);
		return true;
	}

	/**
	 * On definit cote serveur la methode de l'interface qui va permettre
	 * d'ecrire sur l'interface le message du client. Renvoie true si l'ecriture
	 * se deroule normalement.
	 * 
	 */
	/*
	 * public boolean serveurEcrit(String nomUtilisateur, String phrase) {
	 * 
	 * boolean valeurDeRetour = true; System.out.println("Le client " +
	 * nomUtilisateur + " a envoye " + phrase); enregistreUser();
	 * enregistreMsg();
	 * 
	 * for (int i = 0; i < listeClient.size(); ++i) { String nomClient =
	 * (String) listeClient.get(i);
	 * 
	 * try { String name = "//" + nomClient + "/chatclient"; ClientInterface
	 * ClientX = (ClientInterface) java.rmi.Naming .lookup(name);
	 * ClientX.notifie(nomUtilisateur, phrase); }
	 * 
	 * catch (Exception e) {
	 * System.err.println("Erreur: impossible de contacter " + nomClient);
	 * valeurDeRetour = false; } }
	 * 
	 * return valeurDeRetour; }
	 */

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