import java.net.Socket;
import org.json.JSONObject ;

class ConnectionThread extends Thread {
	private Pod pod;
	private Socket client;

	public ConnectionThread(Pod pod, Socket client) {
		this.pod = pod;
		this.client = client;
	}

	/**
	 * Traite la requête.
	 */
	public void run() {
		// Le traitement se déroule en trois phases:
		// - lectures des données dans le socket.
		// - séparation de la commande et des arguments.
		// - envoi des arguments de la commande au service approprié.

		// Phase 1:

		// Phase 2:
		String[] tmp = inputData.split(" ", 2);

		// Phase 3:
		// On lance la commande avec les arguments
		pod.runCommand(tmp[0], new JSONObject(tmp[1]));
	}
	
	/**
	 * Route la commande vers le service adéquat.
	 * @param command
	 * @param arguments
	 */
	public void runCommand(String command, Socket socket, JSONObject arguments) {
		services.get(command).execute(socket, arguments);
	}
	
}
