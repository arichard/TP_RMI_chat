import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Serveur pour le chat utilisant l'API RMI
 * 
 * @author Antoine RICHARD, Anais MANGOLD
 * 
 */
public class Chat_serveur extends UnicastRemoteObject implements
		ServeurInterface {

	protected ArrayList<String> listeClient;
	protected ArrayList<String> listeMsg;
	

	/**
	 * Constructeur de la classe serveur
	 */
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
				result = "* cet utilisateur est deja connecte *";
				System.out.println(result);
				return result;
			}
		}

		listeClient.add(nomUtilisateur);
		result = "* nouvel utilisateur connecte : " + nomUtilisateur + " *";
		System.out.println(result);
		return result;
	}

	/**
	 * Cette methode permet d'afficher la liste des utilisateurs
	 */
	public boolean afficherUsers() throws RemoteException {
		System.out.println("=================================");
		System.out.println("Voici la liste des utilisateurs :");
		// pour chaque utilisateur dans la liste
		for (int i = 0; i < listeClient.size(); i++) {
			// on affiche son nom
			System.out.println(listeClient.get(i));
		}
		System.out.println("=================================");
		return true;
	}

	/**
	 * Cette methode permet de virer un utilisateur
	 */
	public String kickerUser(String nomUtilisateur) throws RemoteException {
		listeClient.remove(nomUtilisateur);
		String result = "* " + nomUtilisateur + " s'est deconnecte *";
		System.out.println(result);
		return result;
	}

	/**
	 * Cette methode permet d'enregistrer un message
	 */
	public boolean enregistreMsg(String nomUtilisateur, String message) {
		String messageComplet = nomUtilisateur + ": " + message;
		listeMsg.add(messageComplet);
		System.out.println(messageComplet);
		return true;
	}
	
	/**
	 * Cette methode permet d'afficher la liste des messages
	 */
	public String afficherMsg(int index) throws RemoteException {
		String Messages = new String();
		Messages = Messages + "=================================\n" + "Voici la liste des derniers messages :\n";
		// on recupere l'index du dernier message deja affiche par le client
		// pour chaque message
		for (int i = index; i < listeMsg.size(); i++) {
			// on l'ajoute a ce qu'il faudra afficher cote Client
			Messages = Messages + listeMsg.get(i) + "\n";
		}
		Messages = Messages + "=================================";
		return Messages;
	}
	
	public int updateIndexDernierMsg() throws RemoteException {
		int newIndex=0;
		newIndex = this.listeMsg.size();
		return newIndex;
	}

	/**
	 * Main du serveur
	 */
	public static void main(String[] args) {
		try {

			// cree une instance de l'objet serveur
			Chat_serveur monServeur = new Chat_serveur();

			// enregistre l'objet cree aupres du serveur de nom
			Naming.rebind("//localhost/chat", monServeur);
			System.out.println("Le serveur de chat a ete enregistre "
					+ "dans le serveur de nom.");

			// permet de fermer le serveur
			Scanner sc = new Scanner(System.in);
			String commande = new String();
			boolean fin = false;
			do {
				commande = sc.nextLine();
				if (commande.startsWith("quit")) {
					fin = true;
					System.out.println("* server closed *");
				}
			} while (fin == false);

		} catch (Exception e) {
			System.err.println("Exception dans Serveur : " + e.getMessage());
			e.printStackTrace();
		}
	}

}