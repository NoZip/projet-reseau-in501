import java.util.Map;

class CommandThread extends Thread {
	private Service service;
	private Socket socket;
	private Map<String, String> arguments;

	public CommandThread(Service service, Socket socket, Map<String, String> arguments) {
		this.service = service;
		this.socket = socket;
		this.arguments = arguments;
	}

	public void run() {
		this.service.execute(socket, arguments);
	}
}
