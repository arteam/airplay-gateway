package ru.bcc.airstage.stream.server;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 13.05.13
 * Time: 12:49
 *
 * @author Artem Prigoda
 */
public class Request {

    @NotNull
    private final String uri;

    @NotNull
    private final Method method;

    @NotNull
    private final Map<String, String> params;

    @NotNull
    private final Map<String, String> headers;

    public Request(@NotNull String uri, @NotNull Method method,
                   @NotNull Map<String, String> params, @NotNull Map<String, String> headers) {
        this.uri = uri;
        this.method = method;
        this.params = params;
        this.headers = headers;
    }

    @NotNull
    public String getUri() {
        return uri;
    }

    @NotNull
    public Method getMethod() {
        return method;
    }

    @NotNull
    public Map<String, String> getParams() {
        return params;
    }

    @NotNull
    public Map<String, String> getHeaders() {
        return headers;
    }
}
