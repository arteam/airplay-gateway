package ru.bcc.airstage.stream;


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
 *
 * @author Artem Prigoda
 */
public class BinaryFileHandler implements SessionHandler {

    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_PLAINTEXT = "text/plain";

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static final Map<String, String> MIME_TYPES = new HashMap<String, String>() {{
        put("css", "text/css");
        put("htm", "text/html");
        put("html", "text/html");
        put("xml", "text/xml");
        put("txt", "text/plain");
        put("asc", "text/plain");
        put("gif", "image/gif");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("mp3", "audio/mpeg");
        put("m3u", "audio/mpeg-url");
        put("mp4", "video/mp4");
        put("ogv", "video/ogg");
        put("flv", "video/x-flv");
        put("mov", "video/quicktime");
        put("swf", "application/x-shockwave-flash");
        put("js", "application/javascript");
        put("pdf", "application/pdf");
        put("doc", "application/msword");
        put("ogg", "application/x-ogg");
        put("zip", "application/octet-stream");
        put("exe", "application/octet-stream");
        put("class", "application/octet-stream");
    }};


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
     * Serves file from homeDir and its' subdirectories (only). Uses only URI, ignores all headers and HTTP parameters.
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
            if (dot >= 0)
                mime = MIME_TYPES.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
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
