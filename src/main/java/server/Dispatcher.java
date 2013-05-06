package server;

import com.google.inject.Inject;
import database.ContentDao;
import database.DeviceDao;
import org.jetbrains.annotations.NotNull;
import server.command.Action;
import server.command.Request;

/**
 * Date: 06.05.13
 * Time: 20:10
 *
 * @author Artem Prigoda
 */
public class Dispatcher {

    @Inject
    JsonConverter jsonConverter;

    @Inject
    DeviceDao deviceDao;

    @Inject
    ContentDao contentDao;

    public Object process(@NotNull Request request) {
        Action action = request.getAction();
        switch (action) {
            case DEVICES:
                return deviceDao.getDevices();
            case CONTENT:
                return contentDao.getContentList();
            case PLAY:
                return "played";
            default:
                return "";
        }
    }
}
