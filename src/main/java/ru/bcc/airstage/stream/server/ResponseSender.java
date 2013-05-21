package ru.bcc.airstage.stream.server;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Date: 5/12/13
 * Time: 1:38 AM
 * A Sender of responses to Apple TV
 *
 * @author Artem Prigoda
 */
class ResponseSender {

    private static final Logger log = Logger.getLogger(ResponseSender.class);
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * Sends given response to the socket.
     */
    void send(@NotNull OutputStream outputStream, @NotNull Response response) {
        String mime = response.mimeType;
        // SDF is not thread-safe
        SimpleDateFormat gmtFmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Status status = response.status;

            PrintWriter pw = new PrintWriter(outputStream);
            pw.print("HTTP/1.1 " + status.getDescription() + " \r\n");
            pw.print("Content-Type: " + mime + "\r\n");

            Map<String, String> headers = response.headers;
            if (headers.get("Date") == null) {
                pw.print("Date: " + gmtFmt.format(new Date()) + "\r\n");
            }

            for (String key : headers.keySet()) {
                String value = headers.get(key);
                pw.print(key + ": " + value + "\r\n");
            }

            pw.print("\r\n");
            pw.flush();

            InputStream data = response.data;

            // This is to support partial sends
            // We use buffer because a file could has big size and not fit in memory
            int amount = data.available();
            int pending = amount;
            int step = 0;
            byte[] buff = new byte[BUFFER_SIZE];
            while (pending > 0) {
                int read = data.read(buff, 0, Math.min(pending, BUFFER_SIZE));
                // All data is read
                if (read == -1) {
                    break;
                }
                outputStream.write(buff, 0, read);
                pending -= read;
                step++;
            }

            log.info("Read " + amount + " in " + step + " steps");
            outputStream.flush();

            data.close();
        } catch (IOException ioe) {
            log.warn("I/O error: " + ioe.getMessage());
        }
    }
}
