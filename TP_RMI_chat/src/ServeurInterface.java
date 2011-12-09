/**
 * Cette classe definit l'interface du serveur.
 */
public interface ServeurInterface extends java.rmi.Remote {

	public boolean enregistreUser(String nomUtilisateur)
			throws java.rmi.RemoteException;

	public boolean removeUser(String nomUtilisateur)
			throws java.rmi.RemoteException;

	public boolean enregistreMsg(String nomUtilisateur, String phrase)
			throws java.rmi.RemoteException;

	public boolean serveurEcrit(String nomUtilisateur, String phrase)
			throws java.rmi.RemoteException;

}