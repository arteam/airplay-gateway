package server;

import com.google.inject.Singleton;

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

    private static final int SIZE = 1;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(9099);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Socket socket = serverSocket.accept();
                        handleConnection(socket);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        });
        System.out.println("Start TCP server at " + serverSocket);
    }

    private void handleConnection(Socket socket) {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            System.out.println("Open " + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String command;
            while ((command = in.readLine()) != null) {
                System.out.println(command);
                try {
                    String output;
                    if (command.equals("listDevices")) {
                        output = "Devices";
                    } else if (command.equals("listContent")) {
                        output = "Contents";
                    } else if (command.startsWith("play")) {
                        String id = command.substring(5, command.length());
                        output = "Played " + id;
                    } else {
                        output = "Unknown command " + command;
                    }
                    System.out.println(output);
                    out.println(output);
                } catch (Exception e) {
                    System.err.println("Command error " + e);
                    out.println("Server error");
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error " + e);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("I/O error " + e);
            }
            System.out.println("Close " + socket);
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
