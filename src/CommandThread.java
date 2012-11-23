import java.util.Map;
import java.net.Socket ;
import org.json.JSONObject;


class CommandThread extends Thread {
	private Service service;
	private Socket socket;
	private JSONObject arguments;

	public CommandThread(Service service, Socket socket, JSONObject arguments) {
		this.service = service;
		this.socket = socket;
		this.arguments = arguments;
	}

	public void run() {
		this.service.execute(socket, arguments);
	}
}
