import java.net.InetAddress ;

import org.json.JSONException;
import org.json.JSONObject ;

public class AddService extends Service {

	public AddService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		UserProfile friendInfo;
		User friend ;
		try {
			friendInfo = UserProfile.fromJSON(arguments);
			friend = new User(friendInfo,new PodLocation(addr,port));
			JSONObject reponse = new JSONObject();
			reponse.put("profile", pod.getOwner().toJSON());
			if(Interface.demandeAmi(friendInfo.getName())){
				pod.addFriend(friend);
				reponse.put("response", true);
			}
			else
				reponse.put("response",false);
			pod.sendCommand(addr,port,"USER",reponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}