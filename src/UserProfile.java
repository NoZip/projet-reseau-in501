import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

class UserProfile {

	protected UUID uuid;
	protected String name;

	public UserProfile(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	public UserProfile(String name) {
		this(UUID.randomUUID(), name);
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return getName();
	}

	public boolean equals(Object o) {
		if(o instanceof  UserProfile){
			UserProfile other = (UserProfile) o;
			return  this.uuid.equals(other.uuid)
					&& this.name.equals(other.name);
		}
		
		return false;
	}

	/**
	 * Transforme un object JSON en UserProfile.
	 * @param source L'objet JSON à sérialiser.
	 * @return Les données utilisateur correspondant à l'objet JSON.
	 * @throws JSONException
	 */
	public static UserProfile fromJSON(JSONObject source) throws JSONException{
		return new UserProfile(UUID.fromString(source.getString("uuid")),
							   source.getString("name"));
	}

	/**
	 * Tranforme l'UserProfile en objet JSON.
	 * @return L'objet JSON correspondant aux données utilisateur.
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put("uuid", uuid.toString());
		json.put("name", name);

		return json;
	}
}
