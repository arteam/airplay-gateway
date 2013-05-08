package ru.bcc.airstage.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
                while (!serverSocket.isClosed() ||
                        !Thread.currentThread().isInterrupted()) {
                    final Socket socket;
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    connectionExecutor.submit(new Runnable() {
                        @Override
                        public void run() {
                            connectionHandler.handle(socket);
                        }
                    });
                }
            }
        }

        );
    }


    /**
     * Stop accepting requests and close socket
     */
    public void stop() {
        log.info("Stopping TCP server...");
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error("Unable close socket", e);
        }

        connectionExecutor.shutdownNow();
        mainExecutor.shutdownNow();

        try {
            connectionExecutor.awaitTermination(1, TimeUnit.MINUTES);
            mainExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error(e);
        }

        log.info("Socket closed: " + serverSocket.isClosed());
        log.info("Connection thread pool terminated: " + connectionExecutor.isTerminated());
        log.info("Dispatcher thread terminated: " + mainExecutor.isTerminated());
    }


}
