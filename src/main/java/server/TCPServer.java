package server;

import com.google.inject.Singleton;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
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

    private static final int SIZE = 2;
    private final ExecutorService executor = Executors.newFixedThreadPool(SIZE);
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(9099);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < SIZE; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        acceptConnection(serverSocket);
                    }
                }
            });
        }
        System.out.println("Start TCP server at " + serverSocket);
    }

    private void acceptConnection(ServerSocket serverSocket) {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            socket = serverSocket.accept();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            writer.write("OK!");
            writer.flush();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (socket != null) socket.close();
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public void stop() {
        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
