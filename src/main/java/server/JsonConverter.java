package server;

import com.google.gson.*;
import com.google.inject.Inject;
import model.Content;
import model.Device;
import server.command.Action;
import server.command.Request;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Date: 30.04.13
 * Time: 18:13
 *
 * @author Artem Prigoda
 */
public class JsonConverter {

    private static final JsonSerializer<Content> CONTENT_SERIALIZER = new JsonSerializer<Content>() {
        @Override
        public JsonElement serialize(final Content content, Type type,
                                     JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("id", content.getId());
            json.addProperty("artist", content.getArtist());
            json.addProperty("name", content.getName());
            json.addProperty("isHd", content.isHd());
            json.addProperty("format", content.getFormat().getName());
            json.addProperty("source", content.getSource().getName());
            return json;
        }
    };
    private static final JsonSerializer<Device> DEVICE_SERIALIZER = new JsonSerializer<Device>() {
        @Override
        public JsonElement serialize(final Device device, Type type,
                                     JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("id", device.getId());
            json.addProperty("name", device.getName());
            json.addProperty("address", device.getAddress().getHostAddress());
            return json;
        }
    };

    private static final JsonDeserializer<Request> REQUEST_DESERIALIZER = new JsonDeserializer<Request>() {
        @Override
        public Request deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String action = jsonObject.get("action").getAsString();
            JsonObject paramsMap = jsonObject.getAsJsonObject("params");

            Map<String, String> params = new HashMap<String, String>();
            if (paramsMap != null) {
                for (Map.Entry<String, JsonElement> entry :
                        paramsMap.entrySet()) {
                    JsonPrimitive jsonPrimitive = entry.getValue().getAsJsonPrimitive();
                    params.put(entry.getKey(), jsonPrimitive.getAsString());
                }
            }

            return new Request(Action.getByCode(action), params);
        }
    };

    private final Gson gson;

    public JsonConverter() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Content.class, CONTENT_SERIALIZER)
                .registerTypeAdapter(Device.class, DEVICE_SERIALIZER)
                .registerTypeAdapter(Request.class, REQUEST_DESERIALIZER)
                .create();
    }

    public String toJson(Object content) {
        return gson.toJson(content);
    }

    public Request fromJson(String json) {
        return gson.fromJson(json, Request.class);
    }
}
