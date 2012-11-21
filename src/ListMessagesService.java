import java.utils.List
import org.json.JSONOArray;

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
	public String execute(Socket socket, JSONObject arguments) {
		List<Messages> messsages = pod.getMessages();

		JSONArray response = new JSONArray();

		Iterator<Message> i = messages.iterator();

		while(i.hasNext()) {
			response.put(i.next().toJSON());
		}

		return response.toString();
	}

}
