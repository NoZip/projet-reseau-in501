<<<<<<< HEAD
import java.util.Map;
import java.net.Socket;
=======
import org.json.JSONObject;
>>>>>>> bf9efa0fe6b2103d6557f5a8b71517a0423967c1

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
