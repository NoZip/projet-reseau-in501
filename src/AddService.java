import java.net.InetAddress ;

import org.json.JSONException;
import org.json.JSONObject ;

/**
 * Classe gérant le service ADD.
 */
public class AddService extends Service {

	/**
	* Création d'une instance de AddService.
	* @param pod Pod auquel est lié le service.
	*/
	public AddService(Pod pod){
		super(pod);
	}
	
	/**
	* Traitement de la commande ADD.
	* @param addr InetAddress de l'expéditeur.
	* @param port Port de l'expéditeur.
	* @param arguments Arguments de la commande.
	*/
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try {
			//on récupère le profile et le port d'écoute de l'expéditeur de la requête, et on créé l'objet User correspondant.
			UserProfile friendProfile = UserProfile.fromJSON(arguments.getJSONObject("profile"));
			PodLocation friendLocation = new PodLocation(addr, arguments.getInt("listening_port"));
			User friend = new User(friendProfile, friendLocation);
			
			//Création de l'objet stokant la réponse.
			JSONObject reponse = new JSONObject();
			reponse.put("profile", pod.getOwner().toJSON());
			
			//Acceptation
			if(Interface.demandeAmi(friendProfile.getName())) {
				pod.addFriend(friend);
				reponse.put("response", true);
			}
			else{
				reponse.put("response",false);
			}
			
			//on envoie la réponse.
			pod.sendCommand(addr, friendLocation.getPort(),"USER",reponse);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}