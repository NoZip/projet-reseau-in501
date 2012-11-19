import java.util.Map;

class CommandThread extends Thread {
	private Service service;

	public CommandThread(Service service, Map<String, String> arguments) {
		this.service = service;
		this.arguments = arguents;
	}

	public void run() {
		this.service.execute()
	}
}
