import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Classe qui implemente le client
 * 
 */
public class Chat_client extends UnicastRemoteObject {

	public Chat_client() throws java.rmi.RemoteException {
		super();
	}

	/*
	 * public void notifie(String nomUtilisateur, String phrase) {
	 * System.out.println(nomUtilisateur + ":" + phrase); }
	 * 
	 * public void startClient() throws IOException { BufferedReader standard =
	 * new BufferedReader(new InputStreamReader( System.in)); while (true) {
	 * String ligne = standard.readLine(); System.out.println("Got line " +
	 * ligne); if (ligne.equals("/quit")) { return; } } }
	 */

	public static void main(String[] args) {
		try {
			// recuperation d'un stub sur l'objet serveur.
			ServeurInterface serveur = (ServeurInterface) Naming
					.lookup("//localhost/chat");

			// on initiliase les variables
			Scanner sc = new Scanner(System.in);
			boolean fin = false;
			// tant qu'on ne demande pas de quitter le client tourne
			do {
				String commande = sc.nextLine();
				// on lance la methode adequate
				if (commande.startsWith("connect")) {
					String nomUtilisateur = commande.split(" ", 2)[1];
					String connect = serveur.enregistreUser(nomUtilisateur);
					System.out.println(connect);
				} else if (commande.startsWith("affiche")) {
					serveur.afficherUsers();
				} else if (commande.startsWith("virer")) {
					String nomUtilisateur = commande.split(" ", 2)[1];
					String virer = serveur.kickerUser(nomUtilisateur);
					System.out.println(virer);
				} else if (commande == "msg") {
					// serveur.enregistreMsg(nomUtilisateur, phrase);
				} else if (commande == "update") {

				} else if (commande.startsWith("quit")){
					fin = true;
				}
			} while (fin=false);

		} catch (Exception e) {
			System.err.println("Exception dans Client : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
