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

	/**
	 * Constructeur du client
	 */
	protected Chat_client() throws RemoteException {
		super();
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
			// tant qu'on ne demande pas de quitter le client tourne
			do {
				String commande = sc.nextLine();
				// on lance la methode adequate
				if (commande.startsWith("connect")) {
					String nomUtilisateur = commande.split(" ", 2)[1];
					String connect = serveur.enregistreUser(nomUtilisateur);
					System.out.println(connect);
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
					String nomUtilisateur = commande.split(" ", 3)[1];
					String message = commande.split(" ", 3)[2];
					serveur.enregistreMsg(nomUtilisateur, message);
				} else if (commande.startsWith("update")) {

				} else if (commande.startsWith("bye")) {
					fin = true;
					System.out.println("* client closed *");
				} else {
					System.out.println("Error !");
				}
			} while (fin == false);

		} catch (Exception e) {
			System.err.println("Exception dans Client : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * Propositions pour la suite :
	 * une classe "message" avec des attributs id, user, text ;
	 * un attribut user pour la classe Client afin de n'avoir qu'un seul user par client
	 * et du coup ne plus avoir à spécifier l'user lorsqu'on envoie un msg
	 * afficher regulierement les messages en stock et pas encore affiches
	 */

}
