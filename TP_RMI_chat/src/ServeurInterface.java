/**
 * Cette classe definit l'interface du serveur.
 */
public interface ServeurInterface extends java.rmi.Remote {

	public boolean enregistreUser() throws java.rmi.RemoteException;

	public void afficherUsers() throws java.rmi.RemoteException;

	public boolean kickerUser() throws java.rmi.RemoteException;

	public boolean enregistreMsg(String nomUtilisateur, String phrase)
			throws java.rmi.RemoteException;

	public boolean serveurEcrit(String nomUtilisateur, String phrase)
			throws java.rmi.RemoteException;

}