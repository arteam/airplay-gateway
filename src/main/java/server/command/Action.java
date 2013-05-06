package server.command;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 06.05.13
 * Time: 17:48
 *
 * @author Artem Prigoda
 */
public enum Action {
    DEVICES("getDevices"),
    CONTENT("getContent"),
    PLAY("play");

    private String code;
    private static Map<String, Action> commands = new HashMap<String, Action>();

    static {
        for (Action action : Action.values()) {
            commands.put(action.code, action);
        }
    }

    private Action(String code) {
        this.code = code;
    }

    @NotNull
    public static Action getByCode(String code) {
        Action action = commands.get(code);
        if (action == null)
            throw new IllegalArgumentException("Unable get command by code " + code);
        return action;
    }


}
