import java.net.InetAddress;
import java.util.Iterator;

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
			friend = new User(friendInfo,new PodLocation(addr,port));
			Iterator<User> it = pod.pendingFriends.iterator();
			while(it.hasNext())
				if(it.next().equals(friend))
					it.remove();
					pod.addFriend(friend);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
