import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe qui implemente le client
 * 
 */
public class Chat_client extends UnicastRemoteObject {

	public Chat_client() throws java.rmi.RemoteException {
		super();
	}

	public void notifie(String nomUtilisateur, String phrase) {
		System.out.println(nomUtilisateur + ":" + phrase);
	}

	public void startClient() throws IOException {
		BufferedReader standard = new BufferedReader(new InputStreamReader(
				System.in));

		while (true) {
			String ligne = standard.readLine();
			System.out.println("Got line " + ligne);
			if (ligne.equals("/quit")) {
				return;
			}
		}
	}

	public static void main(String[] args) {

		try {
			Chat_client monClient = new Chat_client();
			java.rmi.Naming.rebind("chatclient", monClient);
			System.out.println("Le client de chat a ete enregistre "
					+ "dans le serveur de nom.");
			monClient.startClient();
		}

		catch (Exception e) {
			System.err.println("Exception dans Client : " + e.getMessage());
			e.printStackTrace();
		}

	}

}
