package server;

import airplay.AirPlayGateway;
import airplay.DeviceResponse;
import airplay.command.PlayCommand;
import com.google.inject.Inject;
import database.ContentDao;
import database.DeviceDao;
import model.Content;
import model.Device;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.command.Action;
import server.command.Request;

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

    /**
     * Process request
     */
    public Object process(@NotNull Request request) {
        Action action = request.getAction();
        switch (action) {
            case DEVICES:
                return deviceDao.getDevices();
            case CONTENT:
                return contentDao.getContentList();
            case PLAY:
                String contentId = request.getParams().get("contentId");
                String deviceId = request.getParams().get("deviceId");
                return playContent(contentId, deviceId);
            default:
                return "Invalid action";
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

        DeviceResponse response = airPlayGateway.sendCommand(new PlayCommand(content.getUrl(), 0.0), device);
        return response.getCode() + " " + response.getMessage();
    }
}
