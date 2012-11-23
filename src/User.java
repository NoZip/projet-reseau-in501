import java.net.InetAddress;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

	protected UserProfile profile;
	protected PodLocation location;
	protected boolean accepted;

	public UserProfile(UserProfile profile, PodLocation location, boolean accepted) {
		this.profile = profile;
		this.location = location;
		this.accepted = accepted;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public PodLocation getLocation() {
		return location;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
