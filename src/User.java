import java.net.InetAddress;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

	protected UserProfile profile;
	protected PodLocation location;
	protected boolean state;

	public UserProfile(UserProfile profile, PodLocation location, boolean state) {
		this.profile = profile;
		this.location = location;
		this.state = state;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public PodLocation getLocation() {
		return location;
	}

	public boolean getState() {
		return state;
	}
}
