package com.github.arteam.stream.server;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Date: 5/12/13
 * Time: 1:23 AM
 * Decoder of HTTP request to {@link Request}
 *
 * @author Artem Prigoda
 */
class Decoder {

    /**
     * Decodes the sent headers and loads the data into Key/value pairs
     */
    @Nullable
    public Request decodeHeader(@NotNull BufferedReader in) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String> headers = new HashMap<String, String>();

            // Read the request line
            String inLine = in.readLine();
            if (inLine == null) {
                return null;
            }

            StringTokenizer st = new StringTokenizer(inLine);
            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException();
            }

            Method method = Method.lookup(st.nextToken());
            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException();
            }

            String uri = st.nextToken();

            // Decode parameters from the URI
            int qmi = uri.indexOf('?');
            if (qmi >= 0) {
                decodeParams(uri.substring(qmi + 1), params);
                uri = decodePercent(uri.substring(0, qmi));
            } else {
                uri = decodePercent(uri);
            }

            // If there's another token, it's protocol version,
            // followed by HTTP headers. Ignore version but parse headers.
            // NOTE: this now forces header names lowercase since they are
            // case insensitive and vary by client.
            if (st.hasMoreTokens()) {
                String line = in.readLine();
                while (line != null && line.trim().length() > 0) {
                    int p = line.indexOf(':');
                    if (p >= 0)
                        headers.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                    line = in.readLine();
                }
            }

            return new Request(uri, method, params, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decodes parameters in percent-encoded URI-format ( e.g. "name=Jack%20Daniels&pass=Single%20Malt" ) and
     * adds them to given Map. NOTE: this doesn't support multiple identical keys due to the simplicity of Map.
     */
    private void decodeParams(String params, Map<String, String> p) {
        StringTokenizer st = new StringTokenizer(params, "&");
        while (st.hasMoreTokens()) {
            String e = st.nextToken();
            int sep = e.indexOf('=');
            if (sep >= 0) {
                p.put(decodePercent(e.substring(0, sep)).trim(),
                        decodePercent(e.substring(sep + 1)));
            } else {
                p.put(decodePercent(e).trim(), "");
            }
        }

    }

    /**
     * Decodes the percent encoding scheme. <br/>
     * For example: "an+example%20string" -> "an example string"
     */
    private String decodePercent(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                switch (c) {
                    case '+':
                        sb.append(' ');
                        break;
                    case '%':
                        sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                        i += 2;
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
