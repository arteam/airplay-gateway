package server.command;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 08.05.13
 * Time: 10:31
 *
 * @author Artem Prigoda
 */
public class Response {

    private int code;

    @NotNull
    private Object value;

    public Response() {
    }

    public Response(int code, @NotNull Object value) {
        this.code = code;
        this.value = value;
    }

    public static Response ok(@NotNull Object value) {
        return new Response(0, value);
    }

    public int getCode() {
        return code;
    }

    @NotNull
    public Object getValue() {
        return value;
    }
}
