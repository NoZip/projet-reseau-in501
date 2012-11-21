import java.net.Socket;
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
	private Map<String, Socket> friends;
	private List<Message> messages;

	public Pod(Map<String, Service> services){
		this.services = new Hashtable<String, Service>();
		this.friends = new Hashtable<String, Socket>();
		this.messages = new Vector<Message>();
	}
	
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
	 * @param command La commande associée au service.
	 * @param service Le service.
	 */
	public addService(String command, Service service) {
		synchronized(services) {
			services.put(command, service);
		}
	}

	/**
	 * Retire un service du pod.
	 * @param command
	 */
	public removeService(String command) {
		synchronized(services) {
			services.remove(command);
		}
	}

	// TODO: Trouver un moyen de gérer les amis.

	/**
	 * Récupère un ami grâce à son nom.
	 * @param name Le nom de l'ami.
	 */
	public Socket getFriend(String name) {
		return friends.get(name);
	}

	/**
	 * Ajoute un ami.
	 * @param name
	 * @param socket
	 */
	public void addFriend(String name, Socket socket) {
		synchronized(friends) {
			friends.put(name, socket);
	}

	/**
	 * Supprime un ami
	 * @param name
	 */
	public void removeFriend(String name) {
		synchronized(friends) {
			friends.remove(name);
		}
	}

	/**
	 * Récupère les messages du pod.
	 */
	public List<Messages> getMessages() {
		return messages.clone();
	}

	/**
	 * Ajoute un message au pod.
	 * @param message
	 */
	public void addMessage(Message message) {
		synchronized(messages) {
			messages.add(message);
		}
	}
}
