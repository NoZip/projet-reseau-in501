import java.util.List;
import java.util.UUID;
import java.util.Iterator;
import java.net.InetAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject ;

/**
 * Classe gérant le service ADD.
 */
public class ListMessagesService extends Service {

	/**
	* Création d'une instance de ListMessagesService.
	* @param pod Pod auquel est lié le service.
	*/
	public ListMessagesService(Pod pod) {
		super(pod);
	}

	/**
	 * Retourne tous les messages du pod.
	 * @param addr InetAddress de l'expéditeur.
	 * @param port Port de l'expéditeur.
	 * @param arguments Arguments de la commande.
	 */
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try {
			// Récupération des arguments
			UUID clientUUID = UUID.fromString(arguments.getString("uuid"));
			
			if(pod.hasFriend(addr, clientUUID)) {
				User client = pod.getFriend(clientUUID);
				
				List<Message> messages = pod.getMessages();
				
				JSONObject json = new JSONObject();
				json.put("uuid", pod.getOwner().getUUID());
				JSONArray jsonMessages = new JSONArray();
				
				// On peuple le JSONArray avec les messages choisis
				Iterator<Message> i = messages.iterator();
				while(i.hasNext()) {
					jsonMessages.put(i.next().toJSON());
				}
				
				json.put("messages", jsonMessages);
				
				// Envoi de la commande MSGBULK
				pod.sendCommand(client.getLocation().getAddress(),
						        client.getLocation().getPort(),
						        "MSGBULK",
						        json);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
