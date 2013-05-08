package ru.bcc.airstage.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 30.04.13
 * Time: 19:21
 * TCP server for accepting request from IPhones
 *
 * @author Artem Prigoda
 */
@Singleton
public class TCPServer {

    private final int port = 9099;
    private final ExecutorService connectionExecutor = Executors.newCachedThreadPool();
    private final ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
    private ServerSocket serverSocket;
    private static final Logger log = Logger.getLogger(TCPServer.class);

    @Inject
    private ConnectionHandler connectionHandler;

    /**
     * Start server at port
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Started TCP server at " + serverSocket);
        mainExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // Handle connections
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        final Socket socket = serverSocket.accept();
                        connectionExecutor.submit(new Runnable() {
                            @Override
                            public void run() {
                                connectionHandler.handle(socket);
                            }

                        });
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        });
    }


    /**
     * Stop accepting requests and close socket
     */
    public void stop() {
        connectionExecutor.shutdown();
        mainExecutor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error(e);
        }
    }


}
