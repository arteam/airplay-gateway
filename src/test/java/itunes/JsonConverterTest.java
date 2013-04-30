package itunes;

import junit.framework.Assert;
import model.Content;
import model.ContentFormat;
import model.ContentSource;
import model.Device;
import org.junit.Test;
import server.JsonConverter;

import java.net.Inet4Address;

/**
 * Date: 30.04.13
 * Time: 18:08
 *
 * @author Artem Prigoda
 */
public class JsonConverterTest {

    JsonConverter jsonConverter = new JsonConverter();

    @Test
    public void testContent() {
        Content content = new Content("442342", "Walk from Colorado", "Set Jones", ContentFormat.MPEG_4, "url", false, ContentSource.ITUNES);
        String json = jsonConverter.toJson(content);
        System.out.println(json);
        Assert.assertEquals("{\"id\":\"442342\",\"source\":\"iTunes\",\"name\":\"Walk from Colorado\",\"format\":\"MPEG-4\",\"artist\":\"Set Jones\",\"isHd\":false}", json);
    }

    @Test
    public void testDevice() throws Exception {
        Device device = new Device("FD902B2431E2ABB97886A8A6DC5F0B25", "Boss AppleTV", Inet4Address.getByName("192.168.52.15"), 7000);
        String json = jsonConverter.toJson(device);
        System.out.println(json);
        Assert.assertEquals("{\"id\":\"FD902B2431E2ABB97886A8A6DC5F0B25\",\"address\":\"192.168.52.15\",\"name\":\"Boss AppleTV\"}", json);
    }
}
