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

	public Pod(Map<String, Service> services){
		this.services = new Hashtable<String, Service>();
		this.friends = new Hashtable<String, Socket>();
		this.messages = new Vector<Message>();
	}

	/**
	 * Lance la boucle d'écoute du Pod.
	 * @param client
	 */
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
