
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

	}
}
