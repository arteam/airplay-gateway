package airplay;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Date: 07.05.13
 * Time: 12:49
 *
 * @author Artem Prigoda
 */
public class DeviceResponseTest {

    @Test
    public void parseResponse() {
        String headers = "HTTP/1.1 405 Method Not Allowed\n" +
                "Date: Thu, 01 Jan 1970 00:10:36 GMT\n" +
                "Content-Length: 0";

        DeviceResponse deviceResponse = new DeviceResponse(headers, null);
        System.out.println(deviceResponse);
        Assert.assertEquals(deviceResponse.getCode(), 405);
        Assert.assertEquals(deviceResponse.getMessage(), "Method Not Allowed");
        Assert.assertEquals(deviceResponse.getHeaders(), new HashMap<String, String>() {{
            put("Date", "Thu, 01 Jan 1970 00:10:36 GMT");
            put("Content-Length", "0");
        }});
        Assert.assertNull(deviceResponse.getContent());
    }

    @Test
    public void parseResponse2() {
        String headers = "HTTP/1.1 200 OK\n" +
                "Date: Thu, 01 Jan 1970 00:10:32 GMT\n" +
                "Content-Length: 0";
        DeviceResponse deviceResponse = new DeviceResponse(headers, null);
        System.out.println(deviceResponse);
        Assert.assertEquals(deviceResponse.getCode(), 200);
        Assert.assertEquals(deviceResponse.getMessage(), "OK");
        Assert.assertEquals(deviceResponse.getHeaders(), new HashMap<String, String>() {{
            put("Date", "Thu, 01 Jan 1970 00:10:32 GMT");
            put("Content-Length", "0");
        }});
        Assert.assertNull(deviceResponse.getContent());
    }

    @Test
    public void parseResponse3() {
        String headers = "HTTP/1.1 200 OK\n" +
                "Date: Thu, 01 Jan 1970 00:56:18 GMT\n" +
                "Content-Type: text/parameters\n" +
                "Content-Length: 39";

        String response = "duration: 85.500000\n" +
                "position: 2.921037";
        DeviceResponse deviceResponse = new DeviceResponse(headers, response);
        System.out.println(deviceResponse);
        Assert.assertEquals(deviceResponse.getCode(), 200);
        Assert.assertEquals(deviceResponse.getMessage(), "OK");
        Assert.assertEquals(deviceResponse.getHeaders(), new HashMap<String, String>() {{
            put("Date", "Thu, 01 Jan 1970 00:56:18 GMT");
            put("Content-Length", "39");
            put("Content-Type", "text/parameters");
        }});
        Assert.assertEquals(deviceResponse.getParams(), new HashMap<String, String>() {{
            put("duration", "85.500000");
            put("position", "2.921037");
        }});
        Assert.assertEquals(deviceResponse.getContent(), response);
    }


}
