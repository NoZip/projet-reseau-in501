import java.net.InetAddress;

import org.json.JSONObject;


public class UserService extends Service {

	public UserService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments){
		User friend = User.fromJSON(arguments);
		pod.addFriend(friend.getName(),friend);
	}
}
