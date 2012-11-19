import java.net.Socket;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.net.ServerSocket;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*; 
import org.json.JSONObject;

public class Pod {
	
	private Map<String, Service> services;
	private Map<String, Socket> friends;
	private List<Message> messages;
	private ServerSocket listeningServer ;
	
	public Pod(Map<String, Service> services){
		this.services = services ;
		this.friends = new Hashtable<String,Socket>();
		this.messages = new Vector<Message>();
	}	
	
	public void listeningServer(){
		try {
			ServerSocket ServSock = new ServerSocket(7777); //creation du  serveur
			while(true){
				Socket sockCo = ServSock.accept(); //on accepte la connexion
				//NomThread monthread = new NomThread(sockCo);//lancer le thread //on lance le thread chargé de l'écoute
				//monThread.run();
			}
		}catch(Exception e){
			;
		}
		
	}
	
	/**
	 * Lance la boucle d'écoute du Pod.
	 * @param client
	 */
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
		    	
		    	if(cpt == 0 & !debut) { //si message finit
		    		parseQuery(buf.toString(),client); //on traite la commande
		    		debut = true ; //pn reinitialise
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
		runCommand(tmp[0],new JSONObject(tmp[1])); //on recupere la commande et les arguments associés (dans un JSONObject)
	}
	
	/**
	 * Route la commande vers le service adéquat.
	 * @param command
	 * @param arguments
	 */
	public void runCommand(String command, JSONObject arguments) {//je sais pas comment faire
		
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
	 * Ajoute un ami.
	 * @param url
	 */
	public void addFriend(String url) {
		
	}
	
	/**
	 * Supprime un ami
	 * @param url
	 */
	public void removeFriend(String url) {
		
	}
	
	/**
	 * Ajoute un message au pod.
	 * @param message
	 */
	public void addMessage(Message message) {
		
	}
	
	/**
	 * Récupère les messages du pod.
	 */
	public void getMessages() {
		
	}
}
