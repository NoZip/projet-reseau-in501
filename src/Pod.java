import java.net.Socket;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

	public class Pod {

		protected Map<String, Service> services;
		protected List<User> friends;
		protected List<Message> messages;
		protected Interface myInterface;

		protected UserProfile owner;

		public Pod(String username){
			this.services = new Hashtable<String, Service>();
			this.friends = new Vector<User>();
			this.messages = new Vector<Message>();
			this.myInterface = new Interface(this);
			this.owner = new UserProfile(username);
		}
		
		public Interface getInterface(){
			return myInterface;
		}

		public void listen(int port) {
			try {
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
			System.out.println("Je reçois une requête");
			// Le traitement se déroule en trois phases:
			// 1: lectures des données dans le socket.
			// 2: séparation de la commande et des arguments.
			// 3: envoi des arguments de la commande au service approprié.
			try {
				InetAddress addr = client.getInetAddress();
				int port = client.getPort();
				// Phase 1:
				ByteBuffer buf = ByteBuffer.allocate(256);
				InputStream is = client.getInputStream();
				byte[] readData = new byte[128];
				while(is.read(readData)>0){
					buf.put(readData) ;
				}
				String inputData = new String(buf.array()) ;
				// Phase 2:
				String[] tmp = new String[2];
				tmp = inputData.split(" ", 2);
				System.out.println(inputData +" "+ tmp.length);

				// Phase 3:
				// On lance la commande avec les arguments
				runCommand(tmp[0], addr, port,new JSONObject(tmp[1]));
				//verifie response et agir en consequence
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
		* @todo Récrire la fonction.
		*/
		public void sendCommand(InetAddress addr, int port, String command, JSONObject arguments) {
			try {
				Socket sock = new Socket(addr,port); //on cree la socket d'ecriture
				OutputStream os = sock.getOutputStream(); //on cree le stream
				StringBuffer tmpBuf = new StringBuffer(command + " " + arguments.toString()) ; //on cree la chaine a envoyer
				String tmp = new String(tmpBuf);
				os.write(tmp.getBytes()); //on l'envoie sous forme de bytes
				os.close();
				sock.close();
			} catch(Exception e){
				;//tant pis
			}
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
		* Ajoute un ami.
		* @param friend L'objet utilisateur représentant l'ami.
		*/
		public void addFriend(User friend) {
			System.out.println("Ami ajouté" + friend.getName());
			synchronized(friends) {
				friends.add(friend);
				System.out.println("Nouveau Friends : " + friends.toString());
			}
		}
		
		public void acceptFriend(User Friend) {
			Iterator<User> it = friends.iterator();
			while(it.hasNext()) {
				User ami = it.next();
				if(ami.equals(Friend)) {
					ami.setAccepted(true);
					break;
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
		* Ajoute un message au pod.
		* @param message le message à ajouter.
		*/
		public void addMessage(Message message) {
			synchronized(messages) {
				messages.add(message);
				System.out.println(message.getContent());
				myInterface.afficherMessage(message.getContent());
			}
		}

		/**
		 * permet de récupérer le profil de l'utilisateur du pod.
		 */
		public UserProfile getOwner() {
			return owner;
		}
		
		public void sendAddFriend(String friendName, InetAddress addr,int port) throws JSONException{
			//sendCommand(addr,port,"ADD",owner.toJSON());
			User  newFriend = new User(new UserProfile(friendName), new PodLocation(addr,port),false);
			addFriend(newFriend);
		}
		
		public void sendMessage(String msg) throws JSONException{
			Iterator<User> it = friends.iterator();
			JSONObject message = new JSONObject();
			message.put("content", msg);
			while(it.hasNext()) {
				User ami = it.next();
				sendCommand(ami.getLocation().getAddress(),ami.getLocation().getPort(),"MSG",message);
				}	
		}
}
