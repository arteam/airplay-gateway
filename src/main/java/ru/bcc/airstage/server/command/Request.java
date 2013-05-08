package ru.bcc.airstage.server.command;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Date: 06.05.13
 * Time: 17:47
 * Represents a request from IPhone client
 *
 * @author Artem Prigoda
 */
public class Request {

    @NotNull
    private final Action action;

    @NotNull
    private final Map<String, String> params;

    public Request(@NotNull Action action, @NotNull Map<String, String> params) {
        this.action = action;
        this.params = params;
    }

    @NotNull
    public Action getAction() {
        return action;
    }

    @NotNull
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Request{action=" + action + ", params=" + params + "}";
    }
}
