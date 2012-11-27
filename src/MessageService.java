
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
	 * postÃ©s depuis une certaine date.
	 * @TODO Prendre en compte l'option "since"
	 */
	@Override
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		System.out.println("Message reçu");
		try {
			pod.getInterface().afficherMessage(Message.fromJSON(arguments).getContent());
		} catch (JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
