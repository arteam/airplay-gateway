package ru.bcc.airstage.server;

import com.google.gson.internal.Streams;
import com.google.inject.name.Named;
import ru.bcc.airstage.airplay.AirPlayGateway;
import ru.bcc.airstage.airplay.DeviceResponse;
import ru.bcc.airstage.airplay.command.PlayCommand;
import com.google.inject.Inject;
import ru.bcc.airstage.airplay.command.ScrubCommand;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.database.DeviceDao;
import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.Device;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.server.command.Action;
import ru.bcc.airstage.server.command.Request;
import ru.bcc.airstage.server.command.Response;
import ru.bcc.airstage.stream.StreamServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                return new Response(0, deviceDao.getDevices());
            case CONTENT:
                return new Response(0, contentDao.getContentList());
            case PLAY:
                String contentId = request.getParams().get("contentId");
                String deviceId = request.getParams().get("deviceId");
                return new Response(0, contentPlayer.playContent(contentId, deviceId));
            default:
                return new Response(1, "Invalid action");
        }
    }


}
