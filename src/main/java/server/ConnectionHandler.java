package server;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.command.Request;

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
            System.out.println("Open " + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String request;
            while ((request = in.readLine()) != null) {
                String response = process(request);
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("I/O error " + e);
        } finally {
            close(socket, in, out);
            System.out.println("Close " + socket);
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
            System.err.println("I/O error " + e);
        }
    }


    /**
     * Convert and process request
     *
     * @param jsonRequest request in json format
     * @return response in json format
     */
    @NotNull
    private String process(@NotNull String jsonRequest) {
        System.out.println(jsonRequest);
        Request request = jsonConverter.fromJson(jsonRequest);
        Object response = dispatcher.process(request);
        String jsonResponse = jsonConverter.toJson(response);
        System.out.println(jsonResponse);
        return jsonResponse;
    }
}
