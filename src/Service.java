import java.util.Map;
import java.net.Socket;

public abstract class Service {
	protected Pod pod;

	public Service(Pod pod) {
		this.pod = pod;
	}

	public Pod getPod() {
		return pod;
	}

	public abstract void execute(Socket socket, Map<String, String> arguments);
}
