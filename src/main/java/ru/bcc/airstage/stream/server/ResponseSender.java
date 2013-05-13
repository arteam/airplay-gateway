package ru.bcc.airstage.stream.server;

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
 *
 * @author Artem Prigoda
 */
public class ResponseSender {

    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * Sends given response to the socket.
     */
    void send(OutputStream outputStream, Response response) {
        String mime = response.mimeType;
        SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Status status = response.status;

            PrintWriter pw = new PrintWriter(outputStream);
            pw.print("HTTP/1.1 " + status.getDescription() + " \r\n");

            if (mime != null) {
                pw.print("Content-Type: " + mime + "\r\n");
            }

            Map<String, String> header = response.headers;
            if (header == null || header.get("Date") == null) {
                pw.print("Date: " + gmtFrmt.format(new Date()) + "\r\n");
            }

            if (header != null) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    pw.print(key + ": " + value + "\r\n");
                }
            }

            pw.print("\r\n");
            pw.flush();

            InputStream data = response.data;
            if (data != null) {
                // This is to support partial sends
                int pending = data.available();
                byte[] buff = new byte[pending];
                int read = data.read(buff);
                System.out.println("Read " + read + " Available: " + pending);
                outputStream.write(buff);
            }
            outputStream.flush();
            if (data != null) data.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
