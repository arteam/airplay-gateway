package ru.bcc.airstage.stream.server;

import com.google.inject.Inject;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private ServerSocket myServerSocket;
    private final ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService workExecutor = Executors.newCachedThreadPool();
    private final HTTPSessionHandler httpSessionHandler;

    @Inject
    public HttpServer(HTTPSessionHandler httpSessionHandler) {
        this.httpSessionHandler = httpSessionHandler;
    }

    public SocketAddress getAddress(){
        return myServerSocket.getLocalSocketAddress();
    }

    public void start(int port){
        start("localhost", port);
    }

    /**
     * Starts the server
     * <p/>
     * Throws an IOException if the socket is already in use
     */
    public void start(String hostname, int port) {
        try {
            myServerSocket = new ServerSocket();
            myServerSocket.bind(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

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
        try {
            myServerSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        workExecutor.shutdown();
        mainExecutor.shutdown();
    }

}
