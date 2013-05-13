package ru.bcc.airstage.stream;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.log4j.Logger;
import ru.bcc.airstage.stream.server.HttpServer;

/**
 * Date: 08.05.13
 * Time: 18:14
 * Server for streaming content
 *
 * @author Artem Prigoda
 */
@Singleton
public class StreamServer {

    private String host = NetworkUtils.ipAddress();

    @Inject
    @Named("streamPort")
    private int port;

    @Inject
    private HttpServer httpServer;

    public void start() {
        httpServer.start(host, port);
    }

    public void stop() {
        httpServer.stop();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
