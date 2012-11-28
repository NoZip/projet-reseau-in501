import java.net.InetAddress;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


public class UserService extends Service {

	public UserService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments){
		UserProfile friendProfile;
		User friend;
		try {
			friendProfile = UserProfile.fromJSON(arguments);
			friend = new User(friendProfile, new PodLocation(addr, port));
			Iterator<PodLocation> it = pod.getPendingFriends().iterator();
			while(it.hasNext()) {
				if(it.next().equals(friend.getLocation())) {
					it.remove();
					pod.addFriend(friend);
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
