import org.json.JSONException;
import org.json.JSONObject ;

public class User {

	protected UserProfile profile;
	protected PodLocation location;
	protected boolean accepted;

	public User(UserProfile profile, PodLocation location) {
		this.profile = profile;
		this.location = location;
	}

	public boolean equals(Object o) {
		if(o instanceof User){
			User user = (User) o;
			return this.location.equals(user.location) && this.profile.getUUID().equals(user.getProfile().getUUID());
		}
		return false;
	}
	
	public UserProfile getProfile() {
		return profile;
	}

	public PodLocation getLocation() {
		return location;
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
		return new User((UserProfile) json.get("profile"), (PodLocation) json.get("location"));
	}
}
