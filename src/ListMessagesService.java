import java.util.Map;


public class ListMessagesService extends Service {

	public ListMessagesService(Pod pod) {
		super(pod);
	}

	/**
	 * Retourne tous les messages du pod.
	 * Il est possible avec l'option "since" de ne retrouver que les messages
	 * postés depuis une certaine date.
	 */
	@Override
	public void execute(Map<String, String> arguments) {
		
	}

}
