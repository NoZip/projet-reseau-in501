/**
 * Classe permettant la création de thread gérant un serveur d'écoute.
 */
public class ServerThread extends Thread {
	
	private Pod pod;
	
	/**
	* Création d'une instance de ServeurThread.
	* @param pod Pod auquel est lié le thread.
	*/
	public ServerThread(Pod pod){
		this.pod = pod ;
	}
	
	/**
	* Gestion du serveur d'écoute.
	*/
	public void run(){
		pod.listen();
	}

}