import java.net.Socket;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.net.ServerSocket;
import java.io.*;
<<<<<<< HEAD
=======
import java.nio.*;
import java.nio.channels.*;
>>>>>>> bf9efa0fe6b2103d6557f5a8b71517a0423967c1
import org.json.JSONObject;

public class Pod {

	private Map<String, Service> services;
	private Map<String, Socket> friends;
	private List<Message> messages;

	public Pod(Map<String, Service> services){
		this.services = new Hashtable<String, Service>();
		this.friends = new Hashtable<String, Socket>();
		this.messages = new Vector<Message>();
	}

	/**
	 * Lance la boucle d'écoute du Pod.
	 * @param client
	 */
<<<<<<< HEAD
	public void listen(Socket client) {
		try{
			InputStream is = client.getInputStream(); //initialisation du stream
		    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8")); //initialisation du reader
		    
		    StringBuffer buf = new StringBuffer() ; //chaine qui va contenir les données reçues
		    char tmpBuf[] = new char[1]; //pour lire charactere par charactere
		    int cpt = 0 ; //compte le nombre de { pour savoir quand se termine la commande
		    boolean debut = true ; // permet de savoir si on est au debut du message ou si on a commence a lire les arguments
		    
		    while(true){
		    	
		    	br.read(tmpBuf,0,1); // on lit charactere par charactere
		    	
		    	if( tmpBuf[0] == ('{') )
		    		cpt++;
		    	else if( tmpBuf[0] == ('}') )
		    		cpt--;
		    	
		    	buf.append(tmpBuf);
		    	
		    	if(cpt == 0 & !debut) { //si message fini
		    		parseQuery(buf.toString(),client); //on traite la commande
		    		debut = true ; //on reinitialise
		    		buf = new StringBuffer() ;
		    	}
		    	
		    }
		}catch(Exception e){
			;
		}
	}
	
	/**
	 * Traite les données récupérés sur le socket et sépare la commande des arguments.
	 * ex: MSG {'mimetype': 'text/plain', 'content': 'Hello Knacki'}
	 * @param inputData
	 */
	public void parseQuery(String inputData,Socket client) {
		String[] tmp = inputData.split(" ");//le separateur peut-etre amene a change
		try { //new JSONObject peut lancer une exception
			runCommand(tmp[0],new JSONObject(tmp[1])); //on recupere la commande et les arguments associés (dans un JSONObject)
		}catch(Exception e){
			; //tant pis
		}
	}
	
=======
	public void listen(int port) {
		ServerSocket serverSocket = new ServerSocket(port); //creation du  serveur

		// Boucle d'écoute
		while(true){
			Socket socket = serverSocket.accept();

			// On redirige le socket qui s'est connecté vers un thread
			ConnectionThread handler = new ConnectionThread(this, socket);
			handler.run();
		}
	}

>>>>>>> bf9efa0fe6b2103d6557f5a8b71517a0423967c1
	/**
	 * Route la commande vers le service adéquat.
	 * @param command
	 * @param arguments
	 */
	public void runCommand(String command, Socket socket, JSONObject arguments) {
		services.get(command).execute(socket, arguments);
	}

	/**
	 * Envoie une commande à un autre Pod
	 * @param url
	 * @param command
	 * @param arguments
	 */
	public void sendCommand(String url, String command, Map<String, String> arguments) {

	}

	/**
	 * Récupère un service grace à sa commande.
	 * @param command
	 */
	public getService(String command) {
		return services.get(command);
	}

	/**
	 * Ajoute un service au pod.
	 * @param command
	 * @param service
	 */
	public synchronized addService(String command, Service service) {

	}

	/**
	 * Retire un service du pod.
	 * @param command
	 */
	public synchronized removeService(String command) {

	}

	/**
	 * Ajoute un ami.
	 * @param url
	 */
	public synchronized void addFriend(String url) {

	}

	/**
	 * Supprime un ami
	 * @param url
	 */
	public synchronized void removeFriend(String url) {

	}

	/**
	 * Récupère les messages du pod.
	 */
	public synchronized void getMessages() {

	}

	/**
	 * Ajoute un message au pod.
	 * @param message
	 */
	public synchronized void addMessage(Message message) {

	}
}
