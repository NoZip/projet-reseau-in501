import java.util.Map;

public abstract class Service {
	protected Pod pod;
	
	public Service(Pod pod) {
		this.pod = pod;
	}
	
	public abstract void execute(Map<String, String> arguments);
}
