package ru.bcc.airstage.server;

import com.google.gson.internal.Streams;
import com.google.inject.name.Named;
import ru.bcc.airstage.airplay.AirPlayGateway;
import ru.bcc.airstage.airplay.DeviceResponse;
import ru.bcc.airstage.airplay.command.PlayCommand;
import com.google.inject.Inject;
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
    private AirPlayGateway airPlayGateway;

    @Inject
    private StreamServer streamServer;

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
                return new Response(0, playContent(contentId, deviceId));
            default:
                return new Response(1, "Invalid action");
        }
    }

    /**
     * Play a content on a device
     *
     * @param contentId id of the content
     * @param deviceId  id of the device
     * @return response from the AirPlay gateway
     */
    @NotNull
    public String playContent(@Nullable String contentId, @Nullable String deviceId) {
        if (contentId == null || deviceId == null) {
            throw new IllegalArgumentException("Content id and device id should be specified");
        }

        Content content = contentDao.getById(contentId);
        Device device = deviceDao.getById(deviceId);

        if (content == null) throw new IllegalArgumentException("Content not found by id=" + contentId);
        if (device == null) throw new IllegalArgumentException("Device not found by id=" + deviceId);

        //http://192.168.52.248:8080/stream?code=425";
        String url = streamServer.getHost() + ":" + streamServer.getPort() + "/stream?code" + contentId;
        DeviceResponse response = airPlayGateway.sendCommand(new PlayCommand(url, 0.0), device);
        return response.getCode() + " " + response.getMessage();
    }
}
