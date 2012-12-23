import org.json.JSONObject;
import java.net.InetAddress ;

/**
 * Classe définissant ce que doit être un service.
 */
public abstract class Service {
	protected Pod pod;

	/**
	* Création d'une instance de Service.
	* @param pod Pod auquel est lié le service.
	*/
	public Service(Pod pod) {
		this.pod = pod;
	}

	/**
	 * Récupère le pod lié au service.
	 * @return Un pod.
	 */
	public Pod getPod() {
		return pod;
	}

	/**
	 * Fonction définissant comment une commande doit être gérée.
	 * @param addr InetAddress de l'expéditeur.
	 * @param port Numéro de port utilisé par l'expéditeur.
	 * @param arguments Argument de la commande.
	 */
	public abstract void execute(InetAddress addr, int port, JSONObject arguments);
}
