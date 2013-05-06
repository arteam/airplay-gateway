package itunes;

import junit.framework.Assert;
import model.Content;
import model.ContentFormat;
import model.ContentSource;
import model.Device;
import org.junit.Test;
import server.JsonConverter;
import server.command.Action;
import server.command.Request;

import java.net.Inet4Address;
import java.util.Arrays;

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
        Assert.assertEquals("{\"id\":\"442342\",\"artist\":\"Set Jones\",\"name\":\"Walk from Colorado\",\"isHd\":false,\"format\":\"MPEG-4\",\"source\":\"iTunes\"}", json);
    }

    @Test
    public void testDevice() throws Exception {
        Device device = new Device("FD902B2431E2ABB97886A8A6DC5F0B25", "Boss AppleTV", Inet4Address.getByName("192.168.52.15"), 7000);
        String json = jsonConverter.toJson(device);
        System.out.println(json);
        Assert.assertEquals("{\"id\":\"FD902B2431E2ABB97886A8A6DC5F0B25\",\"name\":\"Boss AppleTV\",\"address\":\"192.168.52.15\"}", json);
    }

    @Test
    public void testDevices() throws Exception {
        Device device1 = new Device("FD902B2431E2ABB97886A8A6DC5F0B25", "Boss AppleTV", Inet4Address.getByName("192.168.52.15"), 7000);
        Device device2 = new Device("EA2323349005247903487r3847231231", "Home AppleTV", Inet4Address.getByName("192.168.52.11"), 7000);
        String json = jsonConverter.toJson(Arrays.asList(device1, device2));
        System.out.println(json);
        Assert.assertEquals("[{\"id\":\"FD902B2431E2ABB97886A8A6DC5F0B25\",\"name\":\"Boss AppleTV\",\"address\":\"192.168.52.15\"},{\"id\":\"EA2323349005247903487r3847231231\",\"name\":\"Home AppleTV\",\"address\":\"192.168.52.11\"}]", json);
    }

    @Test
    public void testGetDevices() throws Exception {
        String json = "{\"action\":\"getDevices\"}";
        Request request = jsonConverter.fromJson(json);
        System.out.println(request);
        Assert.assertEquals(Action.DEVICES, request.getAction());
        Assert.assertTrue(request.getParams().isEmpty());
    }

    @Test
    public void testGetContent() throws Exception {
        String json = "{\"action\":\"getContent\"}";
        Request request = jsonConverter.fromJson(json);
        System.out.println(request);
        Assert.assertEquals(Action.CONTENT, request.getAction());
        Assert.assertTrue(request.getParams().isEmpty());
    }

    @Test
    public void testPlay() throws Exception {
        String json = "{\"action\":\"play\", \"params\" : {\"contentId\" : 11, \"deviceId\" : 7}}";
        Request request = jsonConverter.fromJson(json);
        System.out.println(request);
        Assert.assertEquals(Action.PLAY, request.getAction());
        Assert.assertTrue(!request.getParams().isEmpty());
        Assert.assertEquals(request.getParams().get("contentId"), "11");
        Assert.assertEquals(request.getParams().get("deviceId"), "7");
    }
}
