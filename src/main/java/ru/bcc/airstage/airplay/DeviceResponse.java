package ru.bcc.airstage.airplay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 27.04.13
 * Time: 11:01
 * HTTP response for AirPlay device
 *
 * @author Artem Prigoda
 */
public class DeviceResponse {

    /**
     * Error code
     */
    private final int code;

    /**
     * Message
     */
    @NotNull
    private final String message;

    /**
     * HTTP Headers
     */
    @NotNull
    private final Map<String, String> headers = new HashMap<String, String>();

    /**
     * Additional content
     */
    @Nullable
    private String content = null;

    /**
     * Text params
     */
    @NotNull
    private final Map<String, String> params = new HashMap<String, String>();

    public DeviceResponse(@NotNull String headers, @Nullable String content) {
        String headerSplit[] = headers.split("\n");

        String httpResponse = headerSplit[0];
        String responseParted[] = httpResponse.split(" ");
        code = Integer.parseInt(responseParted[1]);
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < responseParted.length; i++) {
            builder.append(responseParted[i]);
            if (i < responseParted.length - 1) {
                builder.append(" ");
            }
        }
        message = builder.toString();

        for (int i = 1; i < headerSplit.length; i++) {
            String header = headerSplit[i];
            int separatorIndex = header.indexOf(": ");
            this.headers.put(header.substring(0, separatorIndex),
                    header.substring(separatorIndex + 2, header.length()));
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
