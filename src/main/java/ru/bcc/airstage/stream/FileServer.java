package ru.bcc.airstage.stream;

import fi.iki.elonen.NanoHTTPD;

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
public class FileServer extends NanoHTTPD {

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
        put("425", "/home/artem/Загрузки/sample_iTunes.mov");
    }};


    public FileServer(int port) {
        super(port);
    }

    public FileServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
        System.out.println(method + " '" + uri + "' ");
        if (method != NanoHTTPD.Method.GET) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: Only get is supported");
        }

        if (uri.equals("stream")) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "URI isn't valid");
        }

        for (String value : header.keySet()) {
            System.out.println("HDR: '" + value + "' = '" + header.get(value) + "'");
        }
        String code = parms.get("code");

        return serveFile(code, header);
    }

    /**
     * Serves file from homeDir and its' subdirectories (only). Uses only URI, ignores all headers and HTTP parameters.
     */
    public NanoHTTPD.Response serveFile(String code, Map<String, String> header) {
        NanoHTTPD.Response res;

                  /*  // Make sure we won't die of an exception later
                    if (!homeDir.isDirectory()) {
                        return new Response(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");
                    }*/
        String path = paths.get(code);
        if (path == null) {
            System.err.println("File by code " + code + " isn't registered");
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found");
        }

        File f = new File(path);
        if (!f.exists()) {
            System.err.println("File by " + code + " doesn't exist");
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found");
        }
        try {
            // Get MIME type from file name extension, if possible
            String mime = null;
            int dot = f.getCanonicalPath().lastIndexOf('.');
            if (dot >= 0)
                mime = MIME_TYPES.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
            if (mime == null)
                mime = NanoHTTPD.MIME_DEFAULT_BINARY;

            // Calculate etag
            String etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());

            // Support (simple) skipping:
            long startFrom = 0;
            long endAt = -1;
            String range = header.get("range");
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
                    res = new NanoHTTPD.Response(NanoHTTPD.Response.Status.RANGE_NOT_SATISFIABLE, NanoHTTPD.MIME_PLAINTEXT, "");
                    res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                    res.addHeader("ETag", etag);
                } else {
                    if (endAt < 0)
                        endAt = fileLen - 1;
                    long newLen = endAt - startFrom + 1;
                    if (newLen < 0)
                        newLen = 0;

                    final long dataLen = newLen;
                    FileInputStream fis = new FileInputStream(f) {
                        @Override
                        public int available() throws IOException {
                            return (int) dataLen;
                        }
                    };
                    fis.skip(startFrom);

                    res = new NanoHTTPD.Response(NanoHTTPD.Response.Status.PARTIAL_CONTENT, mime, fis);
                    res.addHeader("Content-Length", "" + dataLen);
                    res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                    res.addHeader("ETag", etag);
                }
            } else {
                if (etag.equals(header.get("if-none-match")))
                    res = new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_MODIFIED, mime, "");
                else {
                    res = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK, mime, new FileInputStream(f));
                    res.addHeader("Content-Length", "" + fileLen);
                    res.addHeader("ETag", etag);
                }
            }
        } catch (IOException ioe) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
        }

        res.addHeader("Accept-Ranges", "bytes"); // Announce that the file server accepts partial content requestes
        return res;
    }
}
