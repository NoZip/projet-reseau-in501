import java.util.Date;
import org.json.JSONObject ;

public class Message {
	protected String content;
	protected Date date;

	public Message(String content, Date date) {
		this.content = content;
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * Tranforme le message en objet JSON.
	 * @return L'objet JSON correspondant au message.
	 * @todo Bien formater la date au format ISO "YYYY-MM-DDThh-mm-ss±hh-mm"
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("content", content);
		json.put("date", date.toString());

		return json;
	}
}
