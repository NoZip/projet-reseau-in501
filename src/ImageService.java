import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.swing.ImageIcon;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe gérant le service IMG.
 */
public class ImageService extends Service{

	/**
	* Création d'une instance de ImageService.
	* @param pod Pod auquel est lié le service.
	*/
	public ImageService(Pod pod) {
		super(pod);
	}

	/**
	* Traitement de la commande IMG.
	* @param addr InetAddress de l'expéditeur.
	* @param port Port de l'expéditeur.
	* @param arguments Arguments de la commande.
	*/
	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try{
			UUID uuid = UUID.fromString(arguments.getString("uuid"));//on récupère l'uuid de l'expéditeur
			
			//on récupère l'url de l'image
			String tmp =  arguments.getString("image");
			URL img = new URL(tmp);
			
			//on génère l'image
			ImageIcon image = new ImageIcon(img);
			
			//si l'expéditeur est un ami on affiche l'image
			if(pod.hasFriend(addr,uuid)){
				User friend = pod.getFriend(uuid);
				pod.getInterface().afficherMessage(friend.getName() + " a partagé l'image :");
				pod.getInterface().afficherImage(image);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
