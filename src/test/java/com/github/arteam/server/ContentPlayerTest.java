package com.github.arteam.server;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;
import com.github.arteam.airplay.AirPlayGateway;
import com.github.arteam.airplay.DeviceResponse;
import com.github.arteam.airplay.command.PlayCommand;
import com.github.arteam.database.ContentDao;
import com.github.arteam.database.DeviceDao;
import com.github.arteam.model.Content;
import com.github.arteam.model.ContentFormat;
import com.github.arteam.model.ContentSource;
import com.github.arteam.model.Device;
import com.github.arteam.stream.StreamServer;

import java.net.Inet4Address;

/**
 * Date: 08.05.13
 * Time: 11:31
 *
 * @author Artem Prigoda
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContentPlayerTest {

    @TestedObject
    ContentPlayer contentPlayer;

    @InjectIntoByType
    Mock<DeviceDao> deviceDaoMock;

    @InjectIntoByType
    Mock<ContentDao> contentDaoMock;

    @InjectIntoByType
    Mock<AirPlayGateway> airPlayGatewayMock;

    @InjectIntoByType
    Mock<StreamServer> streamServerMock;


    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayButContentNotExist() throws Exception {
        contentPlayer.playContent("18", "25");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayButDeviceNotExist() throws Exception {
        contentDaoMock.returns(new Content("18", "Walk from Colorado", "Set Jones",
                ContentFormat.MPEG_4, "url", false, ContentSource.ITUNES)).getById("18");

        contentPlayer.playContent("18", "25");
    }

    @Test
    public void testGetPlay() throws Exception {
        contentDaoMock.returns(new Content("18", "Walk from Colorado", "Set Jones",
                ContentFormat.MPEG_4, "url", false, ContentSource.ITUNES)).getById("18");
        Device device = new Device("25", "Home AppleTV", Inet4Address.getByName("192.168.52.11"), 7000);
        deviceDaoMock.returns(device).getById("25");

        String headers = "HTTP/1.1 200 OK\n" +
                "Date: Thu, 01 Jan 1970 00:10:32 GMT\n" +
                "Content-Length: 0";
        DeviceResponse deviceResponse = new DeviceResponse(headers, null);
        airPlayGatewayMock.returns(deviceResponse).sendCommand(new PlayCommand("http://192.168.52.248:8080/stream?code=18", 0.0), device);

        streamServerMock.returns("192.168.52.248").getHost();
        streamServerMock.returns(8080).getPort();

        String result = contentPlayer.playContent("18", "25");
        System.out.println(result);
        Assert.assertEquals(result, "200 OK");
    }


}
