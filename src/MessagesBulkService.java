import java.net.InetAddress;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe gérant le service MessagesBulk.
 */
public class MessagesBulkService extends Service {

	/**
	* Création d'une instance de MessagesBulk.
	* @param pod Pod auquel est lié le service.
	*/
	public MessagesBulkService(Pod pod) {
		super(pod);
	}

	/**
	* Traitement de la commande MessagesBulk.
	* @param addr InetAddress de l'expéditeur.
	* @param port Port de l'expéditeur.
	* @param arguments Arguments de la commande.
	*/
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try {
			// Récupération des arguments
			UUID clientUUID = UUID.fromString(arguments.getString("uuid"));
			JSONArray jsonMessages = arguments.getJSONArray("messages");
			
			List<Message> messages = new Vector<Message>();			
			for (int i = 0; i < jsonMessages.length(); ++i) {
				messages.add(Message.fromJSON(jsonMessages.getJSONObject(i)));
			}
			
			if(pod.hasFriend(addr, clientUUID)) {
				User friend = pod.getFriend(clientUUID);
				
				for (Message message : messages) {
					pod.getInterface().afficherMessage(friend.getName() + " dit : "+ message.getContent());
				}
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
