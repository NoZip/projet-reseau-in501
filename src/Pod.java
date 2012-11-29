import java.util.Iterator;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class Pod {

	protected Map<String, Service> services;
	protected List<User> friends;
	protected List<PodLocation> pendingFriends;
	protected List<Message> messages;
	protected int listeningPort;

	protected UserProfile owner;
	
	protected Interface myInterface;

	public Pod(String username, int port){
		this.services = new Hashtable<String, Service>();
		this.friends = new Vector<User>();
		this.pendingFriends = new Vector<PodLocation>();
		this.messages = new Vector<Message>();
		this.listeningPort = port;
		
		this.owner = new UserProfile(username);
		
		this.myInterface = new Interface(this);
	}
	
	/**
	* Récupère un service grace à sa commande.
	* @param command la commande liée au service.
	* @return le service demandé.
	*/
	public Service getService(String command) {
		return services.get(command);
	}

	/**
	* Ajoute un service au pod.
	* @param command La commande à laquelle le service sera associé.
	* @param service Le service à rajouter.
	*/
	public void addService(String command, Service service) {
		synchronized(services) {
			services.put(command, service);
		}
	}

	/**
	* Retire un service du pod.
	* @param command La commande à supprimer.
	*/
	public void removeService(String command) {
		synchronized(services) {
			services.remove(command);
		}
	}

	/**
	 * permet de récupérer le liste d'amis du pod.
	 */
	public List<User> getFriendList() {
		List<User> res = new Vector<User>();
		Iterator<User> it = friends.iterator();
		while(it.hasNext())
			res.add(it.next());
		return res;
	}
	
	/**
	 * Vérifie si un ami existe dns la liste d'amis du pod.
	 * @param friend l'ami à chercher.
	 * @return true si l'ami est dans la liste, false sinon.
	 */
	public boolean hasFriend(User friend) {
		return friends.contains(friend);
	}

	/**
	* Ajoute un ami.
	* @param friend L'objet utilisateur représentant l'ami.
	*/
	public void addFriend(User friend) {
		// Permet d'éviter les doublons d'amis
		if (!friends.contains(friend)) {
			synchronized(friends) {
				friends.add(friend);
				System.out.println("Ami ajouté: " + friend.getName());
			}
		}
	}

	/**
	* Supprime un ami.
	* @param index La position de l'ami à supprimer dans la liste.
	*/
	public void removeFriend(int index) {
		synchronized(services) {
			friends.remove(index);
		}
	}
	
	public List<PodLocation> getPendingFriends() {
		return pendingFriends;
	}
	
	public void addPendingFriend(PodLocation friendLocation) {
		if (!pendingFriends.contains(friendLocation)) {
			
			// On ajoute l'ami aux requetes en cours
			synchronized(pendingFriends) {
				pendingFriends.add(friendLocation);
			}
			
			System.out.println(friendLocation.toString());
			
			// On envoir une commande ADD au Pod avec lequel on veut se lier
			JSONObject json = new JSONObject();
			
			try {
				json.put("profile", owner.toJSON());
				json.put("listening_port", listeningPort);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
				
			sendCommand(friendLocation.getAddress(),
						friendLocation.getPort(),
						"ADD",
						json);
		}
	}

	/**
	* Récupère les messages du pod.
	* @return Une liste de messages.
	*/
	public List<Message> getMessages() {
		List<Message> res = new Vector<Message>();
		Iterator<Message> it = messages.iterator();
		while(it.hasNext())
			res.add(it.next());
		return res;
	}

	/**
	* Ajoute un message au pod et l'envoie aux amis.
	* @param message le message à ajouter.
	*/
	public void addMessage(Message message) {
		System.out.println(message.getContent());
		
		// On ajoute le message au pod
		synchronized(messages) {
			messages.add(message);
		}
			
		//On envoie le message à tous les amis
		Iterator<User> it = friends.iterator();
		while(it.hasNext()) {
			User friend = it.next();
			try {
				sendCommand(friend.getLocation().getAddress(),
							friend.getLocation().getPort(),
							"MSG",
							message.toJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getListeningPort(){
		return listeningPort;
	}

	/**
	 * permet de récupérer le profil de l'utilisateur du pod.
	 */
	public UserProfile getOwner() {
		return owner;
	}
	
	public Interface getInterface(){
		return myInterface;
	}

	/**
	 * Lance la boucle d'écoute du pod.
	 */
	public void listen() {
		try {
			int port = this.listeningPort;
			ServerSocket serverSocket = new ServerSocket(port); //creation du serveur

			// Boucle d'écoute
			while(true){
				Socket socket = serverSocket.accept();

				// On redirige le socket qui s'est connecté vers un thread
				ConnectionThread handler = new ConnectionThread(this, socket);
				handler.start();
			}
		} catch(Exception e){
			;//tant pis
		}
	}

	/**
	 * Traite la requête.
	 * @param client Le socket du pod qui a envoyé la commande.
	 */
	public void handleRequest(Socket client) {
		System.out.println("Je reçois une commande");
		// Le traitement se déroule en trois phases:
		// 1: lectures des données dans le socket.
		// 2: séparation de la commande et des arguments.
		// 3: envoi des arguments de la commande au service approprié.
		try {
			InetAddress addr = client.getInetAddress();
			int port = client.getPort();
			PodLocation clientLocation = new PodLocation(addr, port);
			System.out.println(clientLocation.toString());
			
			// Phase 1:
			ByteBuffer buffer = ByteBuffer.allocate(256);
			InputStream in = client.getInputStream();
			byte[] readData = new byte[128];
			
			while(in.read(readData)>0){
				buffer.put(readData) ;
			}
			
			String inputData = new String(buffer.array()) ;
			
			// Phase 2:
			String[] tmp = new String[2];
			tmp = inputData.split(" ", 2);
			System.out.println("Commande: " + tmp[0]);
			System.out.println("Arguments: " + tmp[1]);

			// Phase 3:
			// On lance la commande avec les arguments
			runCommand(tmp[0], addr, port, new JSONObject(tmp[1]));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("il y a eu un problème");
		}
	}

	/**
	 * Route la commande vers le service adéquat.
	 * @param command La commande à lancer.
	 * @param client Le socket corresondant au pod qui a envoyé la commande.
	 * @param arguments Les arguments de la commande.
	 */
	public void runCommand(String command, InetAddress addr, int port, JSONObject arguments) {
		services.get(command).execute(addr, port, arguments);
	}

	/**
	* Envoie une commande à un autre Pod
	* @param url L'adresse du pod auquel envoyer la commande.
	* @param command La commande à envoyer.
	* @param arguments Les arguments de la commande.
	*/
	public void sendCommand(InetAddress addr, int port, String command, JSONObject arguments) {
		System.out.println("Envoi d'une commande");
		System.out.println(addr.toString() + ':' + port);
		System.out.println("Commande: " + command);
		System.out.println("Arguments: " + arguments.toString());
		
		try {
			Socket sock = new Socket(addr, port); //on cree la socket d'ecriture
			OutputStream os = sock.getOutputStream(); //on cree le stream
			StringBuffer tmpBuf = new StringBuffer(command + " " + arguments.toString()) ; //on cree la chaine a envoyer
			String tmp = new String(tmpBuf);
			os.write(tmp.getBytes()); //on l'envoie sous forme de bytes
			os.close();
			sock.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
