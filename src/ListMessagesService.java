import java.util.List;
import java.net.InetAddress;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject ;

public class ListMessagesService extends Service {
	
	private static final String NOM_COMMANDE = "MSG";

	public ListMessagesService(Pod pod) {
		super(pod);
	}

	/**
	 * Retourne tous les messages du pod.
	 * Il est possible avec l'option "since" de ne retrouver que les messages
	 * postés depuis une certaine date.
	 * @todo Prendre en compte l'option "since"
	 */
	@Override
	public void execute(InetAddress addr, int port,JSONObject arguments) {
		List<Message> messages = pod.getMessages();

		Iterator<Message> i = messages.iterator();

		while(i.hasNext()) {
			try {
				pod.sendCommand(addr,port,NOM_COMMANDE,i.next().toJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

}
