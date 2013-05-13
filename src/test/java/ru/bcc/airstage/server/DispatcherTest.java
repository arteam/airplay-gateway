package ru.bcc.airstage.server;

import com.google.gson.internal.Streams;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;
import ru.bcc.airstage.airplay.AirPlayGateway;
import ru.bcc.airstage.airplay.DeviceResponse;
import ru.bcc.airstage.airplay.command.PlayCommand;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.database.DeviceDao;
import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.ContentFormat;
import ru.bcc.airstage.model.ContentSource;
import ru.bcc.airstage.model.Device;
import ru.bcc.airstage.server.command.Action;
import ru.bcc.airstage.server.command.Request;
import ru.bcc.airstage.server.command.Response;
import ru.bcc.airstage.stream.StreamServer;

import java.net.Inet4Address;
import java.util.Collections;
import java.util.HashMap;

/**
 * Date: 08.05.13
 * Time: 11:31
 *
 * @author Artem Prigoda
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DispatcherTest {

    @TestedObject
    Dispatcher dispatcher;

    @InjectIntoByType
    Mock<DeviceDao> deviceDaoMock;

    @InjectIntoByType
    Mock<ContentDao> contentDaoMock;

    @Test
    public void testGetDevices() throws Exception {
        dispatcher.process(new Request(Action.DEVICES, Collections.EMPTY_MAP));
        deviceDaoMock.assertInvoked().getDevices();
    }

    @Test
    public void testGetContent() throws Exception {
        dispatcher.process(new Request(Action.CONTENT, Collections.EMPTY_MAP));
        contentDaoMock.assertInvoked().getContentList();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayWithoutParams() throws Exception {
        dispatcher.process(new Request(Action.PLAY, Collections.EMPTY_MAP));
    }


}
