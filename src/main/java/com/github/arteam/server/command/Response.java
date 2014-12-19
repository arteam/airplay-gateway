package com.github.arteam.server.command;

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

    private Action type;

    public Response() {
    }

    public Response(int code, @NotNull Object value, Action type) {
        this.code = code;
        this.value = value;
        this.type = type;
    }

    public Action getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    @NotNull
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", value=" + value +
                '}';
    }
}
