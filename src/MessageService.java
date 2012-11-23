
import java.net.InetAddress;
import java.text.ParseException;

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
		try {
			pod.addMessage(Message.fromJSON(arguments));
		} catch (JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
