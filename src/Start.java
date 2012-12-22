import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Iterator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Start {

	public static void main(String[] args){
		final Pod pod = new Pod(args[0],Integer.parseInt(args[1]));
		pod.addService("MSG", new MessageService(pod));
		pod.addService("ADD", new AddService(pod));
		pod.addService("USER", new UserService(pod));
		pod.addService("DEL", new DeleteService(pod));
		pod.addService("IMG", new ImageService(pod));
		pod.addService("LISTMSG", new ListMessagesService(pod));
		pod.addService("MSGBULK", new MessagesBulkService(pod));
		ServerThread thread = new ServerThread(pod);
		thread.start();
		Interface mine = pod.getInterface();
		mine.addWindowListener(new CloseWindow(mine));
		mine.setVisible(true);

		File f = new File(pod.getOwner().getName() + ".ami.social ");
		System.out.println("***" + f.isFile());
		if(f.isFile()){
			try {
				FileInputStream fileReader= new FileInputStream(f);
				byte[] tmp = new byte[fileReader.available()];
				fileReader.read(tmp);
				fileReader.close();
				
				JSONArray friend = new JSONArray( new String(tmp) );
				
				for(int i = 0; i < friend.length() ; i++) {
					String[] infoFriendTrie = ((String) friend.get(i)).split(":");
					infoFriendTrie[0] = (infoFriendTrie[0].split("/"))[1];
					PodLocation friendLocation = new PodLocation(InetAddress.getByName(infoFriendTrie[0]),
																 Integer.parseInt(infoFriendTrie[1]));
					pod.addPendingFriend(friendLocation);
				}
				
			} catch (FileNotFoundException e) {
				;//si le fichier n'existe pas, on ne fait rien
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		Timer timer = new Timer();
        timer.schedule (new TimerTask() {
            public void run()
            {
            	Iterator<PodLocation> it = pod.getPendingFriends().iterator();
            	while(it.hasNext()){
            		PodLocation pl = it.next();
            		try {
            			JSONObject json = new JSONObject() ;
        				json.put("profile", pod.owner.toJSON());
        				json.put("listening_port", pod.listeningPort);
            			pod.sendCommand(pl.getAddress(),
        						pl.getPort(),
        						"ADD",
        						json);
        			}
        			catch (JSONException e) {
        				e.printStackTrace();
        			}
            	}
            }
        }, 60000); //toutes les minutes on réessaye d'envoyer une invvitation aux personnes n'ayant pas répondu.
		
	}
	
}
