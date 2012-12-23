import java.util.Iterator;
import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Classe gérant le service User.
 */
public class UserService extends Service {

	/**
	* Création d'une instance de UserService.
	* @param pod Pod auquel est lié le service.
	*/
	public UserService(Pod pod){
		super(pod);
	}
	
	/**
	* Traitement de la commande USER.
	* @param addr InetAddress de l'expéditeur.
	* @param port Port de l'expéditeur.
	* @param arguments Arguments de la commande.
	*/
	public void execute(InetAddress addr, int port, JSONObject arguments){
		try {
			Iterator<PodLocation> it = pod.getPendingFriends().iterator();
			
			//on vérifie qu'il s'agit bien d'une réponse à un ADD en parcourant la liste des pendingsFriends.
			while(it.hasNext()) {
				PodLocation friendLocation = it.next();
				
				if(friendLocation.address.equals(addr)) { //s'il fait parti des amis en attente
					UserProfile friendProfile = UserProfile.fromJSON(arguments.getJSONObject("profile"));
					
					if(arguments.getBoolean("response")){ //s'il a accepté la demande
						User friend = new User(friendProfile, friendLocation);
						Interface.resultatInvitation(friendProfile.getName(), true); //on l'indique � l'utilisateur.
						pod.addFriend(friend); //on le rajoute dans la liste d'ami.
						
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
					else { //sinon on indique son refus.
						Interface.resultatInvitation(friendProfile.getName(), false);
					}
					
					it.remove(); //on le retire de la liste des pendingFriends.
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
