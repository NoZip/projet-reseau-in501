import java.net.InetAddress

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
}
