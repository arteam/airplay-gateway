package server;

import model.Content;
import model.Device;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.command.Action;
import server.command.Request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 30.04.13
 * Time: 18:13
 *
 * @author Artem Prigoda
 */
public class JsonConverter {

    JSONParser parser = new JSONParser();

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


    @SuppressWarnings("unchecked")
    public Request fromJson(String json) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            String action = (String) jsonObject.get("action");
            Map<String, Object> params = (Map<String, Object>) jsonObject.get("params");
            if (params == null) {
                params = Collections.EMPTY_MAP;
            }
            return new Request(Action.getByCode(action), params);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
