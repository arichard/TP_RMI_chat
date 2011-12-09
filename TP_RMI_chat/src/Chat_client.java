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
			Scanner scCommande = new Scanner(System.in);
			String commande = new String();
			// tant qu'on ne demande pas de quitter le client tourne
			do {
				// on attend l'entree clavier
				commande = scCommande.nextLine();
				// on lance la methode adequate
				if (commande == "connect") {
					serveur.enregistreUser();
				} else if (commande == "affiche") {
					serveur.afficherUsers();
				} else if (commande == "virer") {
					serveur.kickerUser();
				}

			} while (commande != "quit");

		} catch (Exception e) {
			System.err.println("Exception dans Client : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
