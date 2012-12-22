import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.swing.ImageIcon;

import org.json.JSONException;
import org.json.JSONObject;


public class ImageService extends Service{

	public ImageService(Pod pod) {
		super(pod);
	}

	public void execute(InetAddress addr, int port, JSONObject arguments) {
		try{
			UUID uuid = UUID.fromString(arguments.getString("uuid"));
			String tmp =  arguments.getString("image");
			URL img = new URL(tmp);
			ImageIcon image = new ImageIcon(img);
			System.out.println(tmp);
			if(pod.hasFriend(addr,uuid)){
				User friend = pod.getFriend(uuid);
				pod.getInterface().afficherMessage(friend.getName() + " a partager l'image :");
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
