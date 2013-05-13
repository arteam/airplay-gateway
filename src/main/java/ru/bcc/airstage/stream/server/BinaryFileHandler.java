package ru.bcc.airstage.stream.server;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.stream.server.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 08.05.13
 * Time: 18:58
 * <p/>
 * Support streaming of binary files
 *
 * @author Paul S. Hawke
 * @author Artem Prigoda
 */
public class BinaryFileHandler implements SessionHandler {

    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_PLAINTEXT = "text/plain";


    private Map<String, String> paths = new HashMap<String, String>() {{
        put("425", "/home/artem/Загрузки/MakeUp.mov");
    }};

    @Override
    @Nullable
    public Response serve(@NotNull Request request) {
        Method method = request.getMethod();
        String uri = request.getUri();

        System.out.println(method + " '" + uri + "' ");
        if (method != Method.GET) {
            return new Response(Status.FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Only GET is supported");
        }

        if (uri.equals("stream")) {
            return new Response(Status.FORBIDDEN, MIME_PLAINTEXT, "URI isn't valid");
        }

        Map<String, String> headers = request.getHeaders();
        for (String value : headers.keySet()) {
            System.out.println("HDR: '" + value + "' = '" + headers.get(value) + "'");
        }

        String code = request.getParams().get("code");
        if (code == null) {
            return new Response(Status.BAD_REQUEST);
        }
        return serveFile(code, headers);
    }

    /**
     * Serves files by code
     */
    @NotNull
    private Response serveFile(@NotNull String code, @NotNull Map<String, String> headers) {
        String path = paths.get(code);
        if (path == null) {
            System.err.println("File by code " + code + " isn't registered");
            return new Response(Status.NOT_FOUND, MIME_PLAINTEXT, "Error 404, file not found");
        }

        File f = new File(path);
        if (!f.exists()) {
            System.err.println("File by " + code + " doesn't exist");
            return new Response(Status.NOT_FOUND, MIME_PLAINTEXT, "Error 404, file not found");
        }

        Response res;
        try {
            // Get MIME type from file name extension, if possible
            String mime = null;
            int dot = f.getCanonicalPath().lastIndexOf('.');
            if (dot >= 0) {
                String extension = f.getCanonicalPath().substring(dot + 1).toLowerCase();
                mime = MimeTypes.get(extension);
            }
            if (mime == null)
                mime = MIME_DEFAULT_BINARY;

            // Calculate etag
            String etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());

            // Support (simple) skipping:
            long startFrom = 0;
            long endAt = -1;
            String range = headers.get("range");
            if (range != null) {
                if (range.startsWith("bytes=")) {
                    range = range.substring("bytes=".length());
                    int minus = range.indexOf('-');
                    try {
                        if (minus > 0) {
                            startFrom = Long.parseLong(range.substring(0, minus));
                            endAt = Long.parseLong(range.substring(minus + 1));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }

            }

            // Change return code and add Content-Range header when skipping is requested
            long fileLen = f.length();
            if (range != null && startFrom >= 0) {
                if (startFrom >= fileLen) {
                    res = new Response(Status.RANGE_NOT_SATISFIABLE, MIME_PLAINTEXT, "");
                    res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                    res.addHeader("ETag", etag);
                } else {
                    if (endAt < 0) endAt = fileLen - 1;
                    long newLen = endAt - startFrom + 1;
                    if (newLen < 0) newLen = 0;

                    final long dataLen = newLen;
                    FileInputStream fis = new FileInputStream(f) {
                        @Override
                        public int available() throws IOException {
                            return (int) dataLen;
                        }
                    };
                    fis.skip(startFrom);

                    res = new Response(Status.PARTIAL_CONTENT, mime, fis);
                    res.addHeader("Content-Length", "" + dataLen);
                    res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                    res.addHeader("ETag", etag);
                }
            } else {
                if (etag.equals(headers.get("if-none-match")))
                    res = new Response(Status.NOT_MODIFIED, mime, "");
                else {
                    res = new Response(Status.OK, mime, new FileInputStream(f));
                    res.addHeader("Content-Length", "" + fileLen);
                    res.addHeader("ETag", etag);
                }
            }
        } catch (Exception ioe) {
            return new Response(Status.FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
        }

        res.addHeader("Accept-Ranges", "bytes"); // Announce that the file server accepts partial content requestes
        return res;
    }


}
