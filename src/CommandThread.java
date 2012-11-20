<<<<<<< HEAD
import java.util.Map;
import java.net.Socket ;
=======
import org.json.JSONObject;
>>>>>>> bf9efa0fe6b2103d6557f5a8b71517a0423967c1

class CommandThread extends Thread {
	private Service service;
	private Socket socket;
	private Map<String, String> arguments;

	public CommandThread(Service service, Socket socket, JSONObject arguments) {
		this.service = service;
		this.socket = socket;
		this.arguments = arguments;
	}

	public void run() {
		this.service.execute(socket, arguments);
	}
}
