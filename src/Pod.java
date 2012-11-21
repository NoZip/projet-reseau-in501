	import java.net.Socket;
	import java.net.InetAddress;
	import java.util.Map;
	import java.util.Hashtable;
	import java.util.List;
	import java.util.Vector;
	import java.net.ServerSocket;
	import java.io.*;
	import java.nio.*;
	import java.nio.channels.*;
	import org.json.JSONObject;

	public class Pod {

		private Map<String, Service> services;
		private Map<String, String> friends;
		private List<Message> messages;

		public Pod(Map<String, Service> services,String username){
			this.services = new Hashtable<String, Service>();
			this.friends = new Hashtable<String, String>();
			this.messages = new Vector<Message>();
		}

		public void listen(int port) {
			try {
				ServerSocket serverSocket = new ServerSocket(port); //creation du serveur

				// Boucle d'écoute
				while(true){
					Socket socket = serverSocket.accept();

					// On redirige le socket qui s'est connecté vers un thread
					ConnectionThread handler = new ConnectionThread(this, socket);
					handler.run();
				}
			} catch(Exception e){
				;//tant pis
			}
		}

		/**
		* Envoie une commande à un autre Pod
		* @param url
		* @param command
		* @param arguments
		* @return La réponse de l'autre Pod.
		*/
		public JSONObject sendCommand(String url, int port, String command, JSONObject arguments) {
			try {
				InetAddress addr = java.net.InetAddress.getByName(url);//on recupere l'adresse correspondant a l'url
				Socket sock = new Socket(addr,port); //on cree la socket d'ecriture
				OutputStream os = sock.getOutputStream(); //on cree le stream
				StringBuffer tmpBuf = new StringBuffer(command + " " + arguments.toString()) ; //on cree la chaine a envoyer
				String tmp = new String(tmpBuf);
				os.write(tmp.getBytes()); //on l'envoie sous forme de bytes
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
		 * Récupère l'url d'un ami.
		 * @param name le nom de l'ami.
		 * @return L'url correspondant à l'ami (de la forme 192.168.1.42:5555).
		 */
		public String getFriendUrl(String name) {
			return friends.get(name);
		}

		/**
		* Ajoute un ami.
		* @param name Le nom de l'ami.
		* @param url Une url du type "192.168.1.42:5555
		*/
		public void addFriend(String name, String url) {
			synchronized(friends) {
				friends.put(name, url);
			}
		}

		/**
		* Supprime un ami.
		* @param name Le nom de l'ami a supprimer.
		*/
		public void removeFriend(String name) {
			synchronized(services) {
				friends.remove(name);
			}
		}

		/**
		* Récupère les messages du pod.
		* @return Une liste de messages.
		*/
		public synchronized List<Message> getMessages() {
			return messages.clone();
		}

		/**
		* Ajoute un message au pod.
		* @param message le message à ajouter.
		*/
		public void addMessage(Message message) {
			synchronized(messages) {
				messages.add(message);
			}
		}
	}
