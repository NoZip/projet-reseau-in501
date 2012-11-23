import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject ;

public class Message {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
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
	 * Transforme un object JSON en Message.
	 * @param source L'objet JSOn à sérialiser.
	 * @return Le message correspondant à l'objet JSON.
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	public static Message fromJSON(JSONObject source) throws JSONException, ParseException {
		return new Message(source.getString("content"),
						   dateFormat.parse(source.getString("date")));
	}

	/**
	 * Tranforme le message en objet JSON.
	 * @return L'objet JSON correspondant au message.
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		json.put("content", content);
		json.put("date", dateFormat.format(date));

		return json;
	}
}
