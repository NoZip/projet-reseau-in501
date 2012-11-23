import org.json.JSONObject

class UserProfile {

	protected String name;

	public UserProfile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Transforme un object JSON en UserProfile.
	 * @param source L'objet JSON à sérialiser.
	 * @return Les données utilisateur correspondant à l'objet JSON.
	 * @throws JSONException
	 */
	public static UserProfile fromJSON(JSONObject source) throws JSONException {
		return new UserProfile(source.getString("name"))
	}

	/**
	 * Tranforme l'UserProfile en objet JSON.
	 * @return L'objet JSON correspondant aux données utilisateur.
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put("name", name);

		return json;
	}
}
