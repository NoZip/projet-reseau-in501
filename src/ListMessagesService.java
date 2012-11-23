import java.util.List;
import java.net.InetAddress;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject ;
import java.net.Socket;

public class ListMessagesService extends Service {

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

		JSONArray response = new JSONArray();

		Iterator<Message> i = messages.iterator();

		while(i.hasNext()) {
			response.put(i.next().toJSON());
		}

		// à finir
		
		//Pod.sendCommand(addr,port,"NomDeLaCommande",Liste des messages);
	}

}
