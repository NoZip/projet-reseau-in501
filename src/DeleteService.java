import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;


public class DeleteService extends Service {

	public DeleteService(Pod pod){
		super(pod);
	}

	public void execute(InetAddress addr, int port, JSONObject arguments) {
		UserProfile friend;
		try {
			friend = UserProfile.fromJSON(arguments);
			if(pod.hasFriend(addr,friend.getUUID())){
				pod.removeFriend(friend.getUUID());
				Interface.afficherSuppression(friend.getName(),true);
				Interface.supprimerAmi(friend);
			}
		} catch (JSONException e) {
			System.out.println("La suppression a echoue.");
			e.printStackTrace();
		}
	}
}
