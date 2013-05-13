package ru.bcc.airstage.stream.server;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 5/12/13
 * Time: 12:51 AM
 * Some HTTP response status codes
 *
 * @author Artem Prigoda
 */
enum Status {
    OK(200, "OK"), CREATED(201, "Created"), NO_CONTENT(204, "No Content"), PARTIAL_CONTENT(206, "Partial Content"), REDIRECT(301,
            "Moved Permanently"), NOT_MODIFIED(304, "Not Modified"), BAD_REQUEST(400, "Bad Request"), UNAUTHORIZED(401,
            "Unauthorized"), FORBIDDEN(403, "Forbidden"), NOT_FOUND(404, "Not Found"), RANGE_NOT_SATISFIABLE(416,
            "Requested Range Not Satisfiable"), INTERNAL_ERROR(500, "Internal Server Error");
    private int requestStatus;

    @NotNull
    private String descr;

    Status(int requestStatus, @NotNull String descr) {
        this.requestStatus = requestStatus;
        this.descr = descr;
    }

    public int getRequestStatus() {
        return this.requestStatus;
    }

    @NotNull
    public String getDescription() {
        return "" + this.requestStatus + " " + descr;
    }
}
