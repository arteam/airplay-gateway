package server.command;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Date: 06.05.13
 * Time: 17:47
 *
 * @author Artem Prigoda
 */
public class Request {

    @NotNull
    private final Action action;

    @NotNull
    private final Map<String, Object> params;

    public Request(@NotNull Action action, @NotNull Map<String, Object> params) {
        this.action = action;
        this.params = params;
    }

    @NotNull
    public Action getAction() {
        return action;
    }

    @NotNull
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Request{action=" + action + ", params=" + params + "}";
    }
}
