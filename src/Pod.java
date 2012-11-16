import java.net.Socket;
import java.util.Map;
import java.util.List;


public class Pod {
	private Map<String, Service> services;
	private Map<String, Socket> friends;
	private List<Message> messages;
	
	/**
	 * Lance la boucle d'écoute du Pod.
	 * @param port
	 */
	public void listen(int port) {
		
	}
	
	/**
	 * Recupère les données sur le socket et sépare la commande des arguments.
	 * ex: MSG {'mimetype': 'text/plain', 'content': 'Hello Knacki'}
	 * @param socket
	 */
	public void parseQuery(Socket socket) {
		
	}
	
	/**
	 * Route la commande vers le service adéquat.
	 * @param command
	 * @param arguments
	 */
	public void runCommand(String command, Map<String, String> arguments) {
		
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
