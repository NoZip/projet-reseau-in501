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
		private Map<String, String[]> friends;
		private List<Message> messages;
		
		public Pod(Map<String, Service> services,String username){
			this.services = new Hashtable<String, Service>();
			this.friends = new Hashtable<String, String[]>();
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
			}catch(Exception e){
				;//tant pis
			}
		}
		
		/**
		* Envoie une commande à un autre Pod
		* @param url
		* @param command
		* @param arguments
		*/
		public void sendCommand(String url, int port, String command, JSONObject arguments) {
			try {
				InetAddress addr = java.net.InetAddress.getByName(url);//on recupere l'adresse correspondant a l'url
				Socket sock = new Socket(addr,port); //on cree la socket d'ecriture
				OutputStream os = sock.getOutputStream(); //on cree le stream
				StringBuffer tmpBuf = new StringBuffer(command + " " + arguments.toString()) ; //on cree la chaine a envoyer
				String tmp = new String(tmpBuf);
				os.write(tmp.getBytes()); //on l'envoie sous forme de bytes
				sock.close();
			}catch(Exception e){
				;//tant pis
			}
		}
		
		/**
		* Récupère un service grace à sa commande.
		* @param command
		*/
		public void getService(String command) {
			;//return services.get(command);
		}
		
		/**
		* Ajoute un service au pod.
		* @param command
		* @param service
		*/
		public synchronized void addService(String command, Service service) {
			services.put(command,service);
		}
		
		/**
		* Retire un service du pod.
		* @param command
		*/
		public synchronized void removeService(String command) {
			services.remove(command);
		}
		
		/**
		* Ajoute un ami.
		* @param url
		*/
		public synchronized void addFriend(String url,int port) {
			;
		}
		
		/**
		* Supprime un ami
		* @param url
		*/
		public synchronized void removeFriend(String name) {
			friends.remove(name);
		}
		
		/**
		* Récupère les messages du pod.
		*/
		public synchronized void getMessages() {
			;
		}
		
		/**
		* Ajoute un message au pod.
		* @param message
		*/
		public synchronized void addMessage(Message message) {
			;
		}
	}