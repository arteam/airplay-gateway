package com.github.arteam.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;
import com.github.arteam.database.ContentDao;
import com.github.arteam.database.DeviceDao;
import com.github.arteam.server.command.Action;
import com.github.arteam.server.command.Request;

import java.util.Collections;

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
