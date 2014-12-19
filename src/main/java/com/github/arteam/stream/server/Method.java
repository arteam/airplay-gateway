package com.github.arteam.stream.server;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 5/12/13
 * Time: 1:07 AM
 *
 * @author Artem Prigoda
 */
enum Method {
    GET, PUT, POST, DELETE;

    @NotNull
    static Method lookup(@NotNull String method) {
        for (Method m : Method.values()) {
            if (m.toString().equalsIgnoreCase(method)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unable lookup method");
    }
}
