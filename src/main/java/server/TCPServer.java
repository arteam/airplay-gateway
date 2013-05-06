package server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.command.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 30.04.13
 * Time: 19:21
 *
 * @author Artem Prigoda
 */
@Singleton
public class TCPServer {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;

    @Inject
    private ConnectionHandler connectionHandler;

    public void start() {
        try {
            serverSocket = new ServerSocket(9099);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                final Socket socket = serverSocket.accept();
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        connectionHandler.handle(socket);
                    }

                });
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        System.out.println("Started TCP server at " + serverSocket);
    }


    public void stop() {
        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}
