import org.json.JSONException;
import org.json.JSONObject ;

public class User {

	protected UserProfile profile;
	protected PodLocation location;
	protected boolean accepted;

	public User(UserProfile profile, PodLocation location, boolean accepted) {
		this.profile = profile;
		this.location = location;
		this.accepted = accepted;
	}

	public boolean equals(User user) {
		return this.location.equals(user.location) ;
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
	
	public String getName(){
		return profile.getName();
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject() ;
		json.put("profile",profile);
		json.put("location",location);
		json.put("accepted",accepted);
		return json ;
	}
	
	public static User fromJSON(JSONObject json) throws JSONException{
		return new User((UserProfile) json.get("profile"), (PodLocation) json.get("location"), (boolean) json.getBoolean("accepted"));
	}
}
