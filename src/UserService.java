
import java.util.Iterator;
import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;


public class UserService extends Service {

	public UserService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments){
		try {
			Iterator<PodLocation> it = pod.getPendingFriends().iterator();
			
			while(it.hasNext()) {
				PodLocation friendLocation = it.next();
				
				if(friendLocation.address.equals(addr)) {
					UserProfile friendProfile = UserProfile.fromJSON(arguments.getJSONObject("profile"));
					
					if(arguments.getBoolean("response")){
						User friend = new User(friendProfile, friendLocation);
						Interface.resultatInvitation(friendProfile.getName(), true);
						pod.addFriend(friend);
					}
					else {
						Interface.resultatInvitation(friendProfile.getName(), false);
					}
					
					it.remove();
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
