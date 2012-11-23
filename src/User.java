import java.net.InetAddress;
import java.net.UnknownHostException;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	protected String name;
	protected InetAddress ipAddress;
	protected int port;
	
	public User(String name, InetAddress ipAddress , int port) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}
	
	public InetAddress getIpAdress() {
		return ipAddress;
	}
	
	public int getPort() {
		return port;
	}
	
	/**
	 * Transforme un object JSON en User.
	 * @param source L'objet JSON à sérialiser.
	 * @return L'utilisateur correspondant à l'objet JSON.
	 * @throws UnknowHostException
	 * @throws JSONException 
	 */
	public static User fromJSON(JSONObject source) throws UnknownHostException, JSONException {
		return new User(source.getString("name"),
						InetAddress.getByName(source.getString("ip_address")),
						source.getInt("port"));
	}
	
	/**
	 * Tranforme l'utilisateur en objet JSON.
	 * @return L'objet JSON correspondant à l'utilisateur.
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		
		json.put("name", name);
		json.put("ip_address", ipAddress.toString());
		json.put("port", port);
		
		return json;
	}
}
