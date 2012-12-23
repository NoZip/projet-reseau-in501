import java.net.Socket;

/**
 * Classe permettant la création de thread gérant une connexion.
 */
class ConnectionThread extends Thread {
	private Pod pod;
	private Socket client;

	/**
	* Création d'une instance de ConnexionThread.
	* @param pod Pod auquel est lié le thread.
	* @param client Le socket du pod qui a demandé la connexion.
	*/
	public ConnectionThread(Pod pod, Socket client) {
		this.pod = pod;
		this.client = client;
	}

	/**
	* Gestion de la connexion
	*/
	public void run() {
		pod.handleRequest(client);
	}
}