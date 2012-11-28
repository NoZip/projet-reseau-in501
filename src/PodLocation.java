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
	
	public String toString() {
		return address.toString() + String.valueOf(port);
	}
}
