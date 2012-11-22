import java.net.Socket;
import org.json.JSONObject ;

class ConnectionThread extends Thread {
	private Pod pod;
	private Socket client;

	public ConnectionThread(Pod pod, Socket client) {
		this.pod = pod;
		this.client = client;
	}

	public void run() {
		pod.handleRequest(client);
	}
}
