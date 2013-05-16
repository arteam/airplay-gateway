package ru.bcc.airstage.server;

import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.ContentFormat;
import ru.bcc.airstage.model.ContentSource;
import ru.bcc.airstage.model.Device;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;
import ru.bcc.airstage.server.command.Action;
import ru.bcc.airstage.server.command.Request;
import ru.bcc.airstage.server.command.Response;

import java.net.Inet4Address;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Date: 08.05.13
 * Time: 11:04
 *
 * @author Artem Prigoda
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConnectionHandlerTest {

    @TestedObject
    ConnectionHandler connectionHandler;

    @InjectIntoByType
    JsonConverter jsonConverter = new JsonConverter();

    @InjectIntoByType
    Mock<Dispatcher> dispatcherMock;

    @Test
    public void testGetDevices() throws Exception {
        Device device1 = new Device("FD902B2431E2ABB97886A8A6DC5F0B25", "Boss AppleTV", Inet4Address.getByName("192.168.52.15"), 7000);
        Device device2 = new Device("EA2323349005247903487r3847231231", "Home AppleTV", Inet4Address.getByName("192.168.52.11"), 7000);
        dispatcherMock.returns(new Response(0, Arrays.asList(device1, device2), Action.DEVICES)).process(new Request(Action.DEVICES, Collections.EMPTY_MAP));
        String jsonResponse = connectionHandler.process("{\"action\":\"getDevices\"}");
        System.out.println(jsonResponse);
        Assert.assertEquals(jsonResponse,
                "{\"code\":0,\"value\":[{\"id\":\"FD902B2431E2ABB97886A8A6DC5F0B25\",\"name\":\"Boss AppleTV\"," +
                        "\"address\":\"192.168.52.15\"},{\"id\":\"EA2323349005247903487r3847231231\"," +
                        "\"name\":\"Home AppleTV\",\"address\":\"192.168.52.11\"}],\"type\":\"DEVICES\"}");
    }

    @Test
    public void testGetContent() throws Exception {
        Content content1 = new Content("442342", "Walk from Colorado", "Set Jones", ContentFormat.MPEG_4, "url", false, ContentSource.ITUNES);
        Content content2 = new Content("5456", "Blocks and Multithreading", "Paul Hegarty", ContentFormat.MPEG_4, "url", true, ContentSource.ITUNES);
        dispatcherMock.returns(new Response(0, Arrays.asList(content1, content2), Action.CONTENT)).process(new Request(Action.CONTENT, Collections.EMPTY_MAP));
        String jsonResponse = connectionHandler.process("{\"action\":\"getContent\"}");
        System.out.println(jsonResponse);
        Assert.assertEquals(jsonResponse, "{\"code\":0,\"value\":[{\"id\":\"442342\",\"artist\":\"Set Jones\"," +
                "\"name\":\"Walk from Colorado\",\"isHd\":false,\"format\":\"MPEG-4\",\"source\":\"iTunes\"}," +
                "{\"id\":\"5456\",\"artist\":\"Paul Hegarty\",\"name\":\"Blocks and Multithreading\",\"isHd\":true," +
                "\"format\":\"MPEG-4\",\"source\":\"iTunes\"}],\"type\":\"CONTENT\"}");
    }

    @Test
    public void testPlayContent() throws Exception {
        dispatcherMock.returns(new Response(0, "200 OK", Action.PLAY)).process(new Request(Action.PLAY, new HashMap<String, String>() {{
            put("contentId", "81");
            put("deviceId", "FD902B2431E2ABB97886A8A6DC5F0B25");
        }}));
        String jsonResponse = connectionHandler.process("{\"action\":\"play\"," +
                "\"params\":{\"contentId\":\"81\",\"deviceId\":\"FD902B2431E2ABB97886A8A6DC5F0B25\"}}");
        System.out.println(jsonResponse);
        Assert.assertEquals(jsonResponse, "{\"code\":0,\"value\":\"200 OK\",\"type\":\"PLAY\"}");
    }

    @Test
    public void testBadRequest() throws Exception {
        String jsonResponse = connectionHandler.process("not json");
        System.out.println(jsonResponse);
        Assert.assertEquals(jsonResponse, "{\"code\":1,\"value\":\"Invalid request\",\"type\":\"UNDEFINED\"}");
    }

    @Test
    public void testInternalError() throws Exception {
        dispatcherMock.raises(new IllegalStateException("Unable find content")).process(new Request(Action.PLAY,
                new HashMap<String, String>() {{
                    put("contentId", "82");
                    put("deviceId", "77");
                }}));
        String jsonResponse = connectionHandler.process("{\"action\":\"play\"," +
                "\"params\":{\"contentId\":\"82\",\"deviceId\":\"77\"}}");
        System.out.println(jsonResponse);
        Assert.assertEquals(jsonResponse, "{\"code\":2,\"value\":\"Internal error\",\"type\":\"PLAY\"}");
    }
}
