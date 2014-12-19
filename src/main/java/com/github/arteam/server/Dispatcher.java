package com.github.arteam.server;

import com.google.inject.Inject;
import com.github.arteam.database.ContentDao;
import com.github.arteam.database.DeviceDao;
import org.jetbrains.annotations.NotNull;
import com.github.arteam.server.command.Action;
import com.github.arteam.server.command.Request;
import com.github.arteam.server.command.Response;

/**
 * Date: 06.05.13
 * Time: 20:10
 * A dispatcher.
 * Delegates work to actual workers according to an action and return a result
 *
 * @author Artem Prigoda
 */
public class Dispatcher {

    @Inject
    private DeviceDao deviceDao;

    @Inject
    private ContentDao contentDao;

    @Inject
    private ContentPlayer contentPlayer;


    /**
     * Process request
     */
    public Response process(@NotNull Request request) {
        Action action = request.getAction();
        switch (action) {
            case DEVICES:
                return new Response(0, deviceDao.getDevices(), Action.DEVICES);
            case CONTENT:
                return new Response(0, contentDao.getContentList(), Action.CONTENT);
            case PLAY:
                String contentId = request.getParams().get("contentId");
                String deviceId = request.getParams().get("deviceId");
                if (contentId == null || deviceId == null) {
                    throw new IllegalArgumentException("Content id and device id should be specified");
                }
                return new Response(0, contentPlayer.playContent(contentId, deviceId), Action.PLAY);
            default:
                return new Response(1, "Invalid action", Action.UNDEFINED);
        }
    }


}
