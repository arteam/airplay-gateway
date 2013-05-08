package ru.bcc.airstage.stream;

import com.google.inject.Inject;
import fi.iki.elonen.NanoHTTPD;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private FileServer fileServer = new FileServer(host, port);

    public void start() {
        try {
            fileServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Server started at " + host + ":" + port);
    }

    public void stop() {
        fileServer.stop();
    }
}
