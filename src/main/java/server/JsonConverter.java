package server;

import model.Content;
import model.Device;
import org.json.simple.JSONObject;

/**
 * Date: 30.04.13
 * Time: 18:13
 *
 * @author Artem Prigoda
 */
public class JsonConverter {

    @SuppressWarnings("unchecked")
    public String toJson(final Content content) {
        return new JSONObject() {{
            put("id", content.getId());
            put("name", content.getName());
            put("artist", content.getArtist());
            put("isHd", content.isHd());
            put("format", content.getFormat().getName());
            put("source", content.getSource().getName());
        }}.toString();
    }

    @SuppressWarnings("unchecked")
    public String toJson(final Device device) {
        return new JSONObject() {{
            put("id", device.getId());
            put("name", device.getName());
            put("address", device.getAddress().getHostAddress());
        }}.toString();
    }
}
