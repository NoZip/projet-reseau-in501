
import java.net.InetAddress;
import java.text.ParseException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe gérant le service Message.
 */
public class MessageService extends Service {

	/**
	* Création d'une instance de MessageService.
	* @param pod Pod auquel est lié le service.
	*/
	public MessageService(Pod pod) {
		super(pod);
	}

	/**
	 * Traitement de la commande Message.
	 * @param addr InetAddress de l'expéditeur.
	 * @param port Port de l'expéditeur.
	 * @param arguments Arguments de la commande.
	 */
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try{
			UUID uuid = UUID.fromString(arguments.getString("uuid")); //on récupère l'uuid de l'expéditeur
			Message myMsg = Message.fromJSON(arguments.getJSONObject("message")); //on récupère le message
			if(pod.hasFriend(addr,uuid)){ //si l'expéditeur est un ami
				User friend = pod.getFriend(uuid);
				pod.getInterface().afficherMessage(friend.getName() + " dit : "+ myMsg.getContent()); //on affiche le message
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
