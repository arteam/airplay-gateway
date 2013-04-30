package airplay;

import airplay.command.DeviceCommand;
import model.Device;
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
public class TCPClient {

    @Inject
    private ConnectionPool connectionPool;

    @NotNull
    public DeviceResponse sendCommand(@NotNull DeviceCommand command, @NotNull Device device) {
        Socket socket = connectionPool.get(device);
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println(command);
            String request = command.request() + "\n";

            output.write(request);
            output.flush();

            String fullResponse = getResponse(input);
            int contentLength = getContentLength(fullResponse);
            String content = getContent(input, contentLength);

            DeviceResponse deviceResponse = new DeviceResponse(fullResponse, content);
            System.out.println("Response: " + fullResponse + content);
            return deviceResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @NotNull
    private String getResponse(@NotNull BufferedReader input) throws IOException {
        StringBuilder fullResponse = new StringBuilder();
        String line;
        while (!(line = input.readLine().trim()).equals("")) {
            fullResponse.append(line).append("\n");
        }
        return fullResponse.toString();
    }


    private int getContentLength(@NotNull String fullResponse) {
        int contentLength = 0;
        if (fullResponse.contains("Content-Length:")) {
            String cls = "Content-Length:";
            int si = fullResponse.indexOf(cls);
            int ei = fullResponse.indexOf("\n", si + cls.length());
            contentLength = Integer.parseInt(fullResponse.substring(si + cls.length(), ei).trim());
        }
        return contentLength;
    }


    @Nullable
    private String getContent(@NotNull BufferedReader input, int contentLength) throws IOException {
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
}
