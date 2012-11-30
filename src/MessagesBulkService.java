import java.net.InetAddress;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MessagesBulkService extends Service {

	public MessagesBulkService(Pod pod) {
		super(pod);
	}

	@Override
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try {
			// Récupération des arguments
			UUID clientUUID = UUID.fromString(arguments.getString("uuid"));
			JSONArray jsonMessages = arguments.getJSONArray("messages");
			
			List<Message> messages = new Vector();			
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
