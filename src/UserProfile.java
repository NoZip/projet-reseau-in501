import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe définissant l'identité d'un utilisateur.
 */
class UserProfile {

	protected UUID uuid;
	protected String name;

	/**
	 * Création d'un UserProfile
	 * @param uuid Identifiant unique pour le UserProfile.
	 * @param name Nom pour le UserProfile.
	 */
	public UserProfile(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	/**
	 * Création d'un UserProfile
	 * @param name Nom pour le UserProfile.
	 */
	public UserProfile(String name) {
		this(UUID.randomUUID(), name);
	}

	/**
	 * Permet de récupérer l'uuid d'un UserProfle.
	 * @return Un uuid.
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Permet de récupérer le nom associé à un UserProfile.
	 * @return Une chaîne de charactère définissant un nom d'utilisateur.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Renvoie une chaîne de charactère définissant un UserProfile.
	 * @return Une chaîne de charactère définissant un UserProfile.
	 */
	public String toString(){
		return getName();
	}

	/**
	 * Test d'égalité.
	 * @param o Objet à tester.
	 * @return true s'ils sont égaux, false sinon.
	 */
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
