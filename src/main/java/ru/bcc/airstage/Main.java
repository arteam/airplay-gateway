package ru.bcc.airstage;

import ru.bcc.airstage.airplay.AirPlayGateway;
import com.google.inject.*;
import ru.bcc.airstage.airplay.command.PlayCommand;
import ru.bcc.airstage.airplay.command.ScrubCommand;
import ru.bcc.airstage.database.ContentDao;
import ru.bcc.airstage.database.DeviceDao;
import ru.bcc.airstage.itunes.ITunesLibraryProvider;
import ru.bcc.airstage.itunes.data.ITunesLibrary;
import ru.bcc.airstage.itunes.data.ITunesTrack;
import ru.bcc.airstage.jmdns.JmdnsGateway;
import ru.bcc.airstage.model.Content;
import ru.bcc.airstage.model.Device;
import org.apache.log4j.Logger;
import ru.bcc.airstage.server.TCPServer;

import java.util.List;

/**
 * Date: 26.04.13
 * Time: 17:41
 *
 * @author Artem Prigoda
 */
@Singleton
public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private AirPlayGateway airPlayGateway;

    private JmdnsGateway jmdnsGateway;

    private ITunesLibraryProvider iTunesLibraryProvider;

    private ContentDao contentDao;

    private DeviceDao deviceDao;

    private TCPServer tcpServer;

    @Inject
    public Main(AirPlayGateway airPlayGateway, JmdnsGateway jmdnsGateway, ITunesLibraryProvider iTunesLibraryProvider,
                ContentDao contentDao, DeviceDao deviceDao, TCPServer tcpServer) {
        this.airPlayGateway = airPlayGateway;
        this.jmdnsGateway = jmdnsGateway;
        this.iTunesLibraryProvider = iTunesLibraryProvider;
        this.contentDao = contentDao;
        this.deviceDao = deviceDao;
        this.tcpServer = tcpServer;
    }


    public void parseLibraryXml() {
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get("./library.xml");
        //ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get();
        for (ITunesTrack track : iTunesLibrary.getTracks().values()) {
            contentDao.addContent(new Content(track));
        }
        log.info("Found content: " + contentDao.getContentList());
    }

    public List<Device> searchDevices() {
        jmdnsGateway.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                jmdnsGateway.close();
            }
        });
        log.info("Device discovering...");
        jmdnsGateway.waitForDevices();
        List<Device> devices = deviceDao.getDevices();
        if (!devices.isEmpty()) {
            log.info("Found devices: " + devices);
        } else {
            log.warn("No available devices");
        }
        return devices;
    }

    public void streamContent(List<Device> devices) {
        if (devices.isEmpty()) {
            return;
        }

        Device device = devices.get(0);

        //String url = "ftp://assets:assets@192.168.52.112/mpeg2_mpa/SOUZMULTFILM/Bremenskie_muziikantii.mpg";
        String url = "http://192.168.52.15:8989/SUI/perlaws/samples/sample_iTunes.mov";
        airPlayGateway.sendCommand(new PlayCommand(url, 0.0), device);

        // Polling
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                continue;
            }
            airPlayGateway.sendCommand(new ScrubCommand(), device);
        }

    }

    public void startTcpServer() {
        tcpServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                tcpServer.stop();
            }
        });
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });

        Main main = injector.getInstance(Main.class);

        main.startTcpServer();
        main.parseLibraryXml();
        List<Device> devices = main.searchDevices();
        //main.streamContent(devices);
    }
}
