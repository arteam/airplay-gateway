package ru.bcc.airstage.airplay;

import ru.bcc.airstage.airplay.command.DeviceCommand;
import ru.bcc.airstage.model.Device;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

import com.google.inject.*;

import java.net.Socket;

/**
 * Date: 26.04.13
 * Time: 21:01
 * TCP client for air devices
 *
 * @author Artem Prigoda
 */
@Singleton
public class AirPlayGateway {

    private static final Logger log = Logger.getLogger(AirPlayGateway.class);
    private static final String CONTENT_LENGTH = "Content-Length:";

    @Inject
    private ConnectionPool connectionPool;

    /**
     * Send a new command to the device
     *
     * @return response from the device
     */
    @NotNull
    public DeviceResponse sendCommand(@NotNull DeviceCommand command, @NotNull Device device) {
        Socket socket = connectionPool.get(device);
        // We cache socket thus don't close it after using
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            log.info(command);
            String request = command.request() + "\n";

            output.write(request);
            output.flush();

            // Get headers and content
            String headers = getHeaders(input);
            int contentLength = extractContentLength(headers);
            String content = extractContent(input, contentLength);

            DeviceResponse deviceResponse = new DeviceResponse(headers, content);
            log.info("Response: " + headers + content);
            return deviceResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the headers from the response
     *
     * @param input represents input channel to socket
     * @return headers
     */
    @NotNull
    private String getHeaders(@NotNull BufferedReader input) throws IOException {
        StringBuilder fullResponse = new StringBuilder();
        String line;
        while (!(line = input.readLine().trim()).isEmpty()) {
            fullResponse.append(line).append("\n");
        }
        return fullResponse.toString();
    }


    /**
     * Extract content length from the headers
     *
     * @param fullResponse string response
     * @return content length (0 if no content)
     */
    private int extractContentLength(@NotNull String fullResponse) {
        int contentLength = 0;
        if (fullResponse.contains(CONTENT_LENGTH)) {
            int si = fullResponse.indexOf(CONTENT_LENGTH);
            int ei = fullResponse.indexOf("\n", si + CONTENT_LENGTH.length());
            contentLength = Integer.parseInt(fullResponse.substring(si + CONTENT_LENGTH.length(), ei).trim());
        }
        return contentLength;
    }


    /**
     * Read additional content if there some in the response
     *
     * @param input         represents input channel to socket
     * @param contentLength content length
     * @return content as string
     */
    @Nullable
    private String extractContent(@NotNull BufferedReader input, int contentLength) throws IOException {
        if (contentLength <= 0) {
            return null;
        }

        StringBuilder content = new StringBuilder(contentLength);
        char buffer[] = new char[1024];
        int read, totalRead = 0;
        do {
            read = input.read(buffer);
            totalRead += read;
            content.append(buffer, 0, read);
        } while (read != -1 && totalRead < contentLength);

        return content.toString();
    }

    public void close() {
        connectionPool.closeAll();
    }
}
