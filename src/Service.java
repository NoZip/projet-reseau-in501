import java.util.Map;

public abstract class Service {
	protected Pod pod;

	public Service(Pod pod) {
		this.pod = pod;
	}

	public getPod() {
		return pod;
	}

	public abstract void execute(Socket socket, Map<String, String> arguments);
}
