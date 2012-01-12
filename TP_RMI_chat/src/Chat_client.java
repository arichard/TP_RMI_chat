import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Classe qui implemente le client
 * 
 * @author Antoine RICHARD, Anais MANGOLD
 * 
 */
public class Chat_client extends UnicastRemoteObject {

	protected Chat_client() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Main du client
	 */
	public static void main(String[] args) {
		try {
			// recuperation d'un stub sur l'objet serveur.
			ServeurInterface serveur = (ServeurInterface) Naming
					.lookup("//localhost/chat");

			// on initiliase les variables
			Scanner sc = new Scanner(System.in);
			boolean fin = false;
			
			// on se connecte avec un id unique "Guestxxx"
			String nickname = "Guest";
			String connectGuest = serveur.enregistreUser(nickname);
			int i=1;
			while (connectGuest.startsWith("* cet utilisateur est deja connecte *")) {
				nickname = nickname + i;
				i++;
				connectGuest = serveur.enregistreUser(nickname);
			}
			System.out.println(connectGuest);
			int dernierMsgAffiche=0;
			
			// tant qu'on ne demande pas de quitter le client tourne
			do {
				String commande = sc.nextLine();
				// on lance la methode adequate
				if (commande.startsWith("connect")) {
					String disconnectGuest = serveur.kickerUser(nickname);
					System.out.println(disconnectGuest);
					nickname = commande.split(" ", 2)[1];
					String connectUser = serveur.enregistreUser(nickname);
					System.out.println(connectUser);
				} else if (commande.startsWith("who")) {
					boolean result = serveur.afficherUsers();
					if (result) {
						System.out.println("Liste affichee");
					}
				} else if (commande.startsWith("kick")) {
					String nomUtilisateur = commande.split(" ", 2)[1];
					String virer = serveur.kickerUser(nomUtilisateur);
					System.out.println(virer);
				} else if (commande.startsWith("send")) {
					String message = commande.split(" ", 2)[1];
					serveur.enregistreMsg(nickname, message);
				} else if (commande.startsWith("update")) {
					String afficheMsg = serveur.afficherMsg(dernierMsgAffiche);
					dernierMsgAffiche = serveur.updateIndexDernierMsg();
					System.out.println(afficheMsg);
				} else if (commande.startsWith("bye")) {
					fin = true;
					String quit = serveur.kickerUser(nickname);
					System.out.println(quit);
					System.out.println("* client closed *");
				} else {
					System.out.println("Erreur ! Commande non reconnue !");
				}
			} while (fin == false);

		} catch (Exception e) {
			System.err.println("Exception dans Client : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
