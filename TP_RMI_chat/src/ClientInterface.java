/**
 * 
 * Cette interface definit la methode qu'un client
 * doit implementer pour etre appele par le serveur.
 */
public interface ClientInterface extends java.rmi.Remote {
    /**
     * Cette methode permet au serveur d'indiquer qu'un 
     * autre client a emis un message.
     */
    void notifie(String nomUtilisateur, String phrase)
	throws java.rmi.RemoteException;
}