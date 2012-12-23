import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe gérant le service DEL.
 */
public class DeleteService extends Service {

	/**
	* Création d'une instance de DeleteService.
	* @param pod Pod auquel est lié le service.
	*/
	public DeleteService(Pod pod){
		super(pod);
	}

	/**
	* Traitement de la commande DEL.
	* @param addr InetAddress de l'expéditeur.
	* @param port Port de l'expéditeur.
	* @param arguments Arguments de la commande.
	*/
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		UserProfile friend;
		try {
			//on récupère les info de l'ami a supprimer
			friend = UserProfile.fromJSON(arguments);
			
			//on vérifie qu'on l'a en ami
			if(pod.hasFriend(addr,friend.getUUID())){ //si oui on le supprime.
				pod.removeFriend(friend.getUUID()); //on le retire de la liste d'amis.
				Interface.afficherSuppression(friend.getName(),true); //on indique la suppression à l'utilisateur.
				Interface.supprimerAmi(friend);//on le retire de l'interface.
			}
		} catch (JSONException e) {
			System.out.println("La suppression a echoue.");
			e.printStackTrace();
		}
	}
}
