import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;


public class UserService extends Service {

	public UserService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments){
		UserProfile friendInfo;
		User friend;
		try {
			friendInfo = UserProfile.fromJSON(arguments);
			friend = new User(friendInfo,new PodLocation(addr,port),false);
			pod.acceptFriend(friend);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
