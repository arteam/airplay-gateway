package ru.bcc.airstage.server;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.server.command.Request;
import ru.bcc.airstage.server.command.Response;

import java.io.*;
import java.net.Socket;

/**
 * Date: 06.05.13
 * Time: 21:23
 * Handler of connection from clients
 * Can be used from many threads
 *
 * @author Artem Prigoda
 */
public class ConnectionHandler {

    private static final Logger log = Logger.getLogger(ConnectionHandler.class);

    @Inject
    private JsonConverter jsonConverter;

    @Inject
    private Dispatcher dispatcher;



    /**
     * Handle connection from client
     *
     * @param socket socket for communication with client
     */
    public void handle(@NotNull Socket socket) {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            log.info("Open " + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String request;
            while ((request = in.readLine()) != null) {
                String response = process(request);
                out.println(response);
            }
        } catch (IOException e) {
            log.error("I/O error " + e);
        } finally {
            close(socket, in, out);
            log.info("Close " + socket);
        }
    }

    /**
     * Close resources
     */
    private void close(@Nullable Socket socket, @Nullable BufferedReader in, @Nullable Writer out) {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            log.error("I/O error " + e);
        }
    }


    /**
     * Convert and process request
     *
     * @param jsonRequest request in json format
     * @return response in json format
     */
    @NotNull
    String process(@NotNull String jsonRequest) {
        log.info("Request: " + jsonRequest);
        Request request;
        try {
            request = jsonConverter.fromJson(jsonRequest);
        } catch (Exception e) {
            log.error("Unable parse " + jsonRequest, e);
            return jsonConverter.toJson(new Response(1, "Invalid request"));
        }

        try {
            Response response = dispatcher.process(request);
            String jsonResponse = jsonConverter.toJson(response);
            log.info("Response: " + jsonResponse);
            return jsonResponse;
        } catch (Exception e) {
            log.error("Internal error", e);
            return jsonConverter.toJson(new Response(2, "Internal error"));
        }
    }
}
