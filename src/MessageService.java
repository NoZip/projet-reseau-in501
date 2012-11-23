
import java.net.InetAddress;
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
		pod.addMessage(Message.fromJSON(arguments));
	}
}
