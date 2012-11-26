import java.net.InetAddress;

import org.json.JSONException;
import org.json.JSONObject;

class PodLocation {

	protected InetAddress address;
	protected int port;

	public PodLocation(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public boolean equals(PodLocation other) {
		return this.address.equals(other.address)
			   && this.port == other.port;
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		json.put("address",address);
		json.put("port", port);
		return json;
	}
		
	public static PodLocation fromJSON(JSONObject json) throws JSONException{
		return new PodLocation((InetAddress) json.get("address"),(int) json.get("port"));
	}
	
}
