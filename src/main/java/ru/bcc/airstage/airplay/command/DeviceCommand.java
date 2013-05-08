package ru.bcc.airstage.airplay.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Abstract command for communication with AIR devices
 */
public abstract class DeviceCommand {

    private static final String POST = "POST";

    @NotNull
    protected Map<String, String> params = new HashMap<String, String>();

    @NotNull
    protected String requestType() {
        return POST;
    }

    /**
     * Build a HTTP-request based on command name and body
     *
     * @param commandName command name (play, stage...)
     * @param body        command body based on spec
     * @return HTTP request with header and body
     * @see <a href="spec">http://nto.github.io/AirPlay.html</a>
     */
    @NotNull
    protected String build(@NotNull String commandName, @Nullable String body) {
        String encodedParams = params.keySet().size() == 0 ? "" : "?";
        try {
            for (String key : params.keySet()) {
                encodedParams += key + "=" + URLEncoder.encode(params.get(key), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        int length = body == null ? 0 : body.length();
        String headerPart = String.format("%s /%s%s HTTP/1.1\n" +
                "Content-Length: %d\n" +
                "User-Agent: MediaControl/1.0\n", requestType(), commandName, encodedParams, length);
        if (body == null || body.length() == 0) {
            return headerPart;
        } else {
            return headerPart + "\n" + body;
        }
    }

    /**
     * Get command as HTTP-request
     *
     * @return HTTP-request
     */
    @NotNull
    public abstract String request();

    @Override
    public String toString() {
        return "Request: " + request();
    }
}
