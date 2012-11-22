import org.json.JSONObject;
import java.net.InetAddress ;


public abstract class Service {
	protected Pod pod;

	public Service(Pod pod) {
		this.pod = pod;
	}

	public Pod getPod() {
		return pod;
	}

	public abstract String execute(InetAddress addr, int port, JSONObject arguments);
}
