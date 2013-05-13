package ru.bcc.airstage.stream.server;


import com.google.inject.Inject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles sessions, i.e. parses the HTTP request and returns the response.
 */
public class HTTPSessionHandler {

    @Inject
    private Decoder decoder;

    @Inject
    private ResponseSender responseSender;

    @Inject
    private SessionHandler sessionHandler;

    public void handle(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Create a BufferedReader for parsing the header.
            BufferedReader hin = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                Map<String, String> pre = new HashMap<String, String>();
                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> header = new HashMap<String, String>();

                // Decode the header into params and header java properties
                Status status = decoder.decodeHeader(hin, pre, params, header);
                if (status == null) {
                    return;
                }

                if (status != Status.OK) {
                    responseSender.send(outputStream, new Response(status));
                    return;
                }

                Method method = Method.lookup(pre.get("method"));
                if (method == null) {
                    responseSender.send(outputStream, new Response(status));
                    return;
                }
                String uri = pre.get("uri");

                // Ok, now do the serve()
                Response r = sessionHandler.serve(uri, method, header, params);
                if (r != null) {
                    responseSender.send(outputStream, r);
                } else {
                    responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
                }
            }
        } catch (Exception e) {
            responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
        } finally {
            try {
                socket.close();
                System.out.println(socket + " closed");
            } catch (IOException e) {
                responseSender.send(outputStream, new Response(Status.INTERNAL_ERROR));
            }
        }
    }


}