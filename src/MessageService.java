
import java.net.InetAddress;
import java.text.ParseException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageService extends Service {

	public MessageService(Pod pod) {
		super(pod);
	}

	/**
	 * Retourne tous les messages du pod.
	 * Il est possible avec l'option "since" de ne retrouver que les messages
	 * postés depuis une certaine date.
	 * @TODO Prendre en compte l'option "since"
	 */
	@Override
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		System.out.println("Message reçu");
		try{
			UUID uuid = UUID.fromString(arguments.getString("uuid"));
			Message myMsg = Message.fromJSON(arguments.getJSONObject("message"));
			if(pod.hasFriend(addr,uuid)){
				User friend = pod.getFriend(uuid);
				pod.getInterface().afficherMessage(friend.getName() + " dit : "+ myMsg.getContent());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
