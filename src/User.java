import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject ;


/**
 * Classe définissant un utilisateur.
 */
public class User {

	protected UserProfile profile;
	protected PodLocation location;

	/**
	 * Création d'un USER.
	 * @param profile UserProfile de l'utilisateur.
	 * @param location PodLocation de l'utilisateur.
	 */
	public User(UserProfile profile, PodLocation location) {
		this.profile = profile;
		this.location = location;
	}

	/**
	 * Test d'égalité.
	 * @param o Objet à tester.
	 * @return true s'ils sont égaux, false sinon.
	 */
	public boolean equals(Object o) {
		if(o instanceof User){
			User user = (User) o;
			return this.location.equals(user.location) && this.profile.getUUID().equals(user.getProfile().getUUID());
		}
		return false;
	}
	
	/**
	 * Récupère le UserProfile associé à un User.
	 * @return Un UserProfile.
	 */
	public UserProfile getProfile() {
		return profile;
	}

	/**
	 * Récupère le podLocation associé à un User.
	 * @return Un PodLocation.
	 */
	public PodLocation getLocation() {
		return location;
	}
	
	/**
	 * Récupère le nom d'un utilisateur.
	 * @return Le nom d'un utilisateur.
	 */
	public String getName(){
		return profile.getName();
	}
	
	/**
	 * Tranforme le User en objet JSON.
	 * @return L'objet JSON correspondant au User.
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject() ;
		json.put("profile",profile);
		json.put("location",location);
		return json ;
	}
	
	/**
	 * Transforme un object JSON en User.
	 * @param json L'objet JSOn à sérialiser.
	 * @return Le User correspondant à l'objet JSON.
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	public static User fromJSON(JSONObject json) throws JSONException{
		return new User((UserProfile) json.get("profile"), (PodLocation) json.get("location"));
	}
	
	/**
	 * Renvoie une chaîne de charactère définissant un User.
	 * @return Une chaîne de charactère définissant un User.
	 */
	public String toString(){
		return getName();
	}
}
