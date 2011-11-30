import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * Serveur pour le chat utilisant l'API RMI
 * @author Antoine
 *
 */
public class Chat_serveur extends UnicastRemoteObject implements Chat {

	protected Chat_serveur() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
