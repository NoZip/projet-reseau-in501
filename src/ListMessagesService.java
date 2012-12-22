import java.util.List;
import java.util.UUID;
import java.util.Iterator;
import java.net.InetAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject ;

public class ListMessagesService extends Service {

	public ListMessagesService(Pod pod) {
		super(pod);
	}

	/**
	 * Retourne tous les messages du pod.
	 * Options:
	 *  - since : ne renvoie que les messages postés depuis une certaine date.
	 *  - limit : limite le nombre de messages à envoyer.
	 * @TODO Implémenter l'option since.
	 * @TODO Implémenter l'option limit.
	 */
	@Override
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
