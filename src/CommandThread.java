import org.json.JSONObject;

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
