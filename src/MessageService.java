
import java.net.InetAddress;
import java.text.ParseException;
import java.util.Iterator;

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
		Iterator<User> it = pod.getFriendList().iterator();
		while(it.hasNext()){
			User friend = it.next();
			if( addr.equals(friend.getLocation().getAddress())){  //on verifie que la personne qui envoie le msg est bien un ami.
				try {
					pod.getInterface().afficherMessage(friend.getName() + " dit : "+ Message.fromJSON(arguments).getContent());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
