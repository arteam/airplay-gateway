package ru.bcc.airstage.stream.server;


import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

/**
 * Handles sessions, i.e. parses the HTTP request and returns the response.
 *
 * @author Artem Prigoda
 */
public class HTTPSessionHandler {

    private static final Logger log = Logger.getLogger(HTTPSessionHandler.class);

    @Inject
    private Decoder decoder;

    @Inject
    private ResponseSender responseSender;

    @Inject
    private SessionHandler sessionHandler;

    public void handle(@NotNull Socket socket) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            keepAlive(in, outputStream);
        } catch (Exception e) {
            log.error("I/O error", e);
            if (outputStream != null) responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
        } finally {
            try {
                socket.close();
                log.info(socket + " closed");
            } catch (IOException e) {
                log.error("Unable close socket", e);
            }
        }
    }

    private void keepAlive(@NotNull BufferedReader in, @NotNull OutputStream outputStream) {
        // We accept keep-alive thus don't close connection while client doesn't break it
        while (true) {
            Request request;
            try {
                request = decoder.decodeHeader(in);
                // Client break connection
                if (request == null) {
                    return;
                }
            } catch (IllegalArgumentException e) {
                responseSender.send(outputStream, new Response(Status.BAD_REQUEST));
                return;
            } catch (Exception e) {
                responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
                return;
            }

            Response r = sessionHandler.serve(request);
            if (r != null) {
                responseSender.send(outputStream, r);
            } else {
                responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
            }
        }
    }


}