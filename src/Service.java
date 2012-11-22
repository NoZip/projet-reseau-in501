import java.util.Map;
import java.net.Socket;
import org.json.JSONObject;


public abstract class Service {
	protected Pod pod;

	public Service(Pod pod) {
		this.pod = pod;
	}

	public Pod getPod() {
		return pod;
	}

	public abstract void execute(Socket socket, JSONObject arguments);
}
