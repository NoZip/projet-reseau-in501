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
			friendProfile = UserProfile.fromJSON(arguments.getJSONObject("user"));
			friend = new User(friendProfile, new PodLocation(addr, arguments.getInt("listening_port")));
			Iterator<PodLocation> it = pod.getPendingFriends().iterator();
			while(it.hasNext()) {
				if(it.next().equals(friend.getLocation())) {
					it.remove();
					if(arguments.getBoolean("response")){
						Interface.resultatInvitation(friendProfile.getName(),true);
						pod.addFriend(friend);
					}
					else
						Interface.resultatInvitation(friendProfile.getName(),false);
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
