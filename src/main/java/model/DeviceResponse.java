package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP response for AirPlay device
 */
public class DeviceResponse {

    private final int code;

    @NotNull
    private final String message;

    @NotNull
    private final Map<String, String> headers = new HashMap<String, String>();

    @Nullable
    private String content = null;

    @NotNull
    private final Map<String, String> params = new HashMap<String, String>();

    public DeviceResponse(@NotNull String headers, @Nullable String content) {
        String headerSplit[] = headers.split("\n");

        String httpResponse = headerSplit[0];
        String responseParted[] = httpResponse.split(" ");
        code = Integer.parseInt(responseParted[1]);
        message = responseParted[2];

        for (int i = 1; i < headerSplit.length; i++) {
            String headerValueSplit[] = headerSplit[i].split(":");
            this.headers.put(headerValueSplit[0], headerValueSplit[1].trim());
        }

        if (content == null) return;

        this.content = content;
        String contentType = this.headers.get("Content-Type");
        if (contentType != null && contentType.equalsIgnoreCase("text/parameters")) {
            for (String paramLine : content.split("\n")) {
                String paramSplit[] = paramLine.split(":");
                params.put(paramSplit[0], paramSplit[1].trim());
            }
        }
    }

    public int getCode() {
        return code;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    @NotNull
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    @NotNull
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "DeviceResponse{" +
                "responseCode=" + code +
                ", responseMessage=" + message +
                ", headerMap=" + headers +
                ", contentParameterMap=" + params +
                ", content=" + content + '}';
    }
}
