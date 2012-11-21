import java.util.Date;

public class Message {
	protected String content;
	protected Date date;

	public Message(String content, Date date) {
		this.content = content;
		this.date = date;
	}

	public getContent() {
		return content;
	}

	public getDate() {
		return date;
	}

	public toJSON() {
		JSONObject json = new JSONObject();

		json.put("content", content);
		json.put("date", date.toString()); // TODO: bien formater la date au format ISO "YYYY-MM-DDThh-mm-ss±hh-mm"
	}
}
