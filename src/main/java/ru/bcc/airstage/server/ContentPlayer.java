package ru.bcc.airstage.server;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bcc.airstage.airplay.AirPlayGateway;
import ru.bcc.airstage.airplay.DeviceResponse;
import ru.bcc.airstage.airplay.command.PlayCommand;
import ru.bcc.airstage.airplay.command.ScrubCommand;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.database.DeviceDao;
import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.Device;
import ru.bcc.airstage.stream.StreamServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 13.05.13
 * Time: 15:22
 *
 * @author Artem Prigoda
 */
public class ContentPlayer {

    private static final Logger log = Logger.getLogger(ContentPlayer.class);

    @Inject
    private DeviceDao deviceDao;

    @Inject
    private ContentDao contentDao;

    @Inject
    private StreamServer streamServer;

    @Inject
    private AirPlayGateway airPlayGateway;

    private ExecutorService scrubExecutor = Executors.newCachedThreadPool();


    /**
     * Play a content on a device
     *
     * @param contentId id of the content
     * @param deviceId  id of the device
     * @return response from the AirPlay gateway
     */
    @NotNull
    public String playContent(@NotNull String contentId, @NotNull String deviceId) {
        Content content = contentDao.getById(contentId);
        final Device device = deviceDao.getById(deviceId);

        if (content == null) throw new IllegalArgumentException("Content not found by id=" + contentId);
        if (device == null) throw new IllegalArgumentException("Device not found by id=" + deviceId);

        //http://192.168.52.248:8080/stream?code=425";
        String url = "http://" + streamServer.getHost() + ":" + streamServer.getPort() + "/stream?code=" + contentId;
        DeviceResponse response = airPlayGateway.sendCommand(new PlayCommand(url, 0.0), device);

        scrubExecutor.submit(new ScrubTask(device));

        return response.getCode() + " " + response.getMessage();
    }

    public void stop() {
        scrubExecutor.shutdownNow();
        log.info("Player is closed");
    }

    /**
     * Task for scrubbing video
     */
    private class ScrubTask implements Runnable {

        private static final int TWO_SECONDS = 2000;

        private Device device;

        private ScrubTask(Device device) {
            this.device = device;
        }

        @Override
        public void run() {
            // Polling
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(TWO_SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    continue;
                }

                // Scrub video while it ended
                try {
                    airPlayGateway.sendCommand(new ScrubCommand(), device);
                } catch (Exception e) {
                    // Video is ended. Let's exit
                    return;
                }
            }
        }

    }
}
