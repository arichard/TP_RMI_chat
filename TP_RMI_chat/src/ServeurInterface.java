/**
 * Cette classe definit l'interface du serveur.
 */
public interface ServeurInterface extends java.rmi.Remote {

	public String enregistreUser(String nomUtilisateur)
			throws java.rmi.RemoteException;

	public void afficherUsers() throws java.rmi.RemoteException;

	public String kickerUser(String nomUtilisateur)
			throws java.rmi.RemoteException;

	public boolean enregistreMsg(String nomUtilisateur, String message)
			throws java.rmi.RemoteException;

	/*
	 * public boolean serveurEcrit(String nomUtilisateur, String phrase) throws
	 * java.rmi.RemoteException;
	 */

}