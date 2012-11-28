import java.net.InetAddress ;

import org.json.JSONException;
import org.json.JSONObject ;

public class AddService extends Service {

	public AddService(Pod pod){
		super(pod);
	}
	
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try {
			UserProfile friendProfile = UserProfile.fromJSON(arguments.getJSONObject("profile"));
			PodLocation friendLocation = new PodLocation(addr, arguments.getInt("listening_port"));
			User friend = new User(friendProfile, friendLocation);
			
			JSONObject reponse = new JSONObject();
			reponse.put("profile", pod.getOwner().toJSON());
			
			if(Interface.demandeAmi(friendProfile.getName())) {
				pod.addFriend(friend);
				reponse.put("response", true);
			}
			else {
				reponse.put("response",false);
			}
			
			pod.sendCommand(addr, friendLocation.getPort(),"USER",reponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}