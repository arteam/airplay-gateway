package com.github.arteam.stream.server;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Basic HTTP server
 *
 * @author Artem Prigoda
 */
public class HttpServer {

    private static final Logger log = Logger.getLogger(HttpServer.class);

    @NotNull
    private ServerSocket myServerSocket;

    @NotNull
    private final ExecutorService mainExecutor = Executors.newSingleThreadExecutor();

    @NotNull
    private final ExecutorService workExecutor = Executors.newCachedThreadPool();

    @NotNull
    private final HTTPSessionHandler httpSessionHandler;

    @Inject
    public HttpServer(HTTPSessionHandler httpSessionHandler) {
        this.httpSessionHandler = httpSessionHandler;
    }


    public void start(int port) {
        start("localhost", port);
    }

    /**
     * Starts the server
     * <p/>
     * Throws an IOException if the socket is already in use
     */
    public void start(@NotNull String hostname, int port) {
        try {
            myServerSocket = new ServerSocket();
            myServerSocket.bind(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.info("Started HTTP stream server at " + myServerSocket);

        mainExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (!myServerSocket.isClosed()) {
                    try {
                        final Socket socket = myServerSocket.accept();
                        workExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                httpSessionHandler.handle(socket);
                            }
                        });
                    } catch (IOException e) {
                        // Socket is closed, let exit from cycle;
                    }
                }
            }
        });
    }

    /**
     * Stops the server.
     */
    public void stop() {
        log.info("Stopping stream server...");
        try {
            myServerSocket.close();
            log.info(myServerSocket + " closed");
        } catch (IOException ioe) {
            log.error("I/O error", ioe);
        }
        workExecutor.shutdownNow();
        mainExecutor.shutdownNow();
        log.info("Executors are terminated");
    }

}
