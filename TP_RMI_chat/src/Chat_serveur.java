import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

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
	public boolean enregistreUser(String nomUtilisateur) {

		// on verifie si le nomUtilisteur est deja enregistre dans listeClient
		for (int i = 0; i < listeClient.size(); ++i) {
			// on stocke l'element i de la listeClient dans nomClient
			String nomClient = (String) listeClient.get(i);
			// on verifie si le nom du client est deja enregistre
			if (nomClient.equals(nomUtilisateur)) {
				return false;
			}
		}

		System.out.println("Un nouveau client de chat : " + nomUtilisateur);
		listeClient.add(nomUtilisateur);
		return true;
	}

	/**
	 * Cette methode permet de retire un utilisateur
	 */
	public boolean removeUser(String nomUtilisateur) {
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
		enregistreUser(nomUtilisateur);
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
			Chat_serveur monServeur = new Chat_serveur();
			java.rmi.Naming.rebind("chatserveur", monServeur);
			System.out.println("Le serveur de chat a ete enregistre "
					+ "dans le serveur de nom.");
		}

		catch (Exception e) {
			System.err.println("Exception dans Serveur : " + e.getMessage());
			e.printStackTrace();
		}
	}

}