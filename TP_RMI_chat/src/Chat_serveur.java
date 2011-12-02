import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


/**
 * Serveur pour le chat utilisant l'API RMI
 * @author Antoine
 *
 */
public class Chat_serveur extends UnicastRemoteObject implements ServeurInterface {

	Vector listeClient = null;
	Vector listeMsg = null;
	
	protected Chat_serveur() throws RemoteException {
		super();
		listeClient = new Vector();
		listeMsg = new Vector ();
		// TODO Auto-generated constructor stub
	}
	
	 /**
     * Cette methode permet d'enregistrer un nouvel utilisateur
     */
	public boolean enregistreUser(String nomUtilisateur) {
    	
    	//On verifie si le nomUtilisteur est deja enregistre dans listeClient
    	for (int i=0;i<listeClient.size();++i) {
    		//on stocke l'element i du vecteur listeClient dans nomClient
    		String nomClient = (String)listeClient.elementAt(i);
    			if (nomClient.equals(nomUtilisateur)) {
    				// on verifie si le nom du client est deja enregistre.
    				return false;
    				}
    	}
    	
    	System.out.println("Un nouveau client de chat : "+nomUtilisateur);
    	listeClient.addElement(nomUtilisateur);
    	return true;
    }
    
    /**
     * Cette methode permet de retire un utilisateur
     */
    
    public boolean removeUser(String nomUtilisateur){
    	listeClient.remove(nomUtilisateur);
    	System.out.println(nomUtilisateur + " s'est deconnecte.");
    	return true;
    }
    
    /**
     * Cette methode permet d'enregistrer les messages de la session de chat
     */
    public boolean enregistreMsg(String nomUtilisateur, String phrase) {
    	
    	listeMsg.addElement(phrase);
    	return true;
    	
    }
    
    /**
     * On definit cote serveur la méthode de l'interface 
     * qui va permettre d'ecrire sur l'interface le message du client.
     * Renvoie true si l'ecriture se déroule normalement.
     * 
     */
    
    public boolean serveurEcrit(String nomUtilisateur, String phrase) {
    	
    	boolean valeurDeRetour = true;
    	System.out.println("Le client "+ nomUtilisateur +" a envoye "+phrase);
    	enregistreUser(nomUtilisateur);
    	enregistreMsg(nomUtilisateur, phrase);
	
    	for (int i=0;i<listeClient.size();++i) {
    		String nomClient = (String)listeClient.elementAt(i);
    		
    		try {
    			String name = "//" + nomClient + "/chatclient";
    			ClientInterface ClientX = (ClientInterface) java.rmi.Naming.lookup(name);
    			ClientX.notifie(nomUtilisateur,phrase);
    		} 
    		
    		catch (Exception e) {
    			System.err.println("Erreur: impossible de contacter " + nomClient);
    			valeurDeRetour = false ;
    		}
    	}
    	
    	return valeurDeRetour;
    }

    /**
     * Ce main permet de lancer le serveur du chat.
     */
    public static void main(String[] args) {
        try {
            Chat_serveur monServeur = new Chat_serveur();
            java.rmi.Naming.rebind("chatserveur", monServeur);
            System.out.println("Le serveur de chat a ete enregistre " + "dans le serveur de nom.");
        } 
        
        catch (Exception e) {
            System.err.println("Exception dans Serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }

}