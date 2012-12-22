
import java.util.Iterator;
import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


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
						
						// Demandes des messages du nouvel ami
						JSONObject json = new JSONObject();
						json.put("uuid", pod.getOwner().getUUID());
						pod.sendCommand(friend.getLocation().getAddress(),
								        friend.getLocation().getPort(),
								        "LISTMSG",
								        json);
						
						// envoi des messages de ce pod à l'ami
						JSONArray jsonMessages = new JSONArray();
						
						Iterator<Message> i = pod.getMessages().iterator();
						while(i.hasNext()) {
							jsonMessages.put(i.next().toJSON());
						}
						
						json.put("messages", jsonMessages);
						
						// Envoi de la commande MSGBULK
						pod.sendCommand(friend.getLocation().getAddress(),
								        friend.getLocation().getPort(),
								        "MSGBULK",
								        json);
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
