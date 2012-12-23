import java.net.InetAddress;

/**
 * Classe permettant de définir l'adresse d'un pod.
 */
class PodLocation {

	protected InetAddress address;
	protected int port;

	/**
	 * Création d'un podLocation.
	 * @param address InetAddress servant à definir le podLocation.
	 * @param port Numero de port servant à definir le podLocation.
	 */
	public PodLocation(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	/**
	 * Récupère l'InetAddress d'un podLocation;
	 * @return Une InetAddress.
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * Récupère le numéro de port d'un podLocation.
	 * @return Un numéro de port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Test d'égalité
	 * @param o Objet à tester.
	 * @return true s'ils sont égaux, false sinon.
	 */
	public boolean equals(Object o) {
		if(o instanceof PodLocation) {
			PodLocation other = (PodLocation) o;
			return this.address.equals(other.address)
					&& this.port == other.port;
		}
		return false;
	}
	
	/**
	 * Renvoie une chaîne de charactère définissant un podLocation.
	 * @return Chaîne de charactère définissant le podLocation.
	 */
	public String toString() {
		return address.toString() + ':' + String.valueOf(port);
	}
}
