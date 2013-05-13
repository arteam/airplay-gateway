package ru.bcc.airstage.stream.server;

/**
 * Date: 5/12/13
 * Time: 1:07 AM
 *
 * @author Artem Prigoda
 */
public enum Method {
    GET, PUT, POST, DELETE;

    static Method lookup(String method) {
        for (Method m : Method.values()) {
            if (m.toString().equalsIgnoreCase(method)) {
                return m;
            }
        }
        return null;
    }
}
