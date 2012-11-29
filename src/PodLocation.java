import java.net.InetAddress;

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

	public boolean equals(Object o) {
		if(o instanceof PodLocation) {
			PodLocation other = (PodLocation) o;
			return this.address.equals(other.address)
					&& this.port == other.port;
		}
		return false;
	}
	
	public String toString() {
		return address.toString() + ':' + String.valueOf(port);
	}
}
