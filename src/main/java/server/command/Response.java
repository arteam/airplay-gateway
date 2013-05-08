package server.command;

/**
 * Date: 08.05.13
 * Time: 10:31
 *
 * @author Artem Prigoda
 */
public class Response {

    private int code;
    private Object value;

    public Response(int code, Object value) {
        this.code = code;
        this.value = value;
    }
}
