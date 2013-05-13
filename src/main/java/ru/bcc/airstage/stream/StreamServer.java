package ru.bcc.airstage.stream;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import ru.bcc.airstage.stream.server.HttpServer;

/**
 * Date: 08.05.13
 * Time: 18:14
 * Server for streaming content
 *
 * @author Paul S. Hawke
 * @author Artem Prigoda
 */
public class StreamServer {

    private static final Logger log = Logger.getLogger(StreamServer.class);

    private String host = NetworkUtils.ipAddress();
    private int port = 8080;

    @Inject
    private HttpServer httpServer;

    public void start() {
        httpServer.start(host, port);
        log.info("Server started at " + httpServer.getAddress());
    }

    public void stop() {
        httpServer.stop();
        log.info("Server stopped");
    }
}
