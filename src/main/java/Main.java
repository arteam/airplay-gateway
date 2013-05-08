import airplay.AirPlayGateway;
import com.google.inject.*;
import airplay.command.PlayCommand;
import airplay.command.ScrubCommand;
import database.ContentDao;
import database.DeviceDao;
import itunes.ITunesLibraryProvider;
import itunes.data.ITunesLibrary;
import itunes.data.ITunesTrack;
import jmdns.JmdnsGateway;
import model.Content;
import model.Device;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import server.TCPServer;

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
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get("./src/main/resources/library.xml");
        log.info(iTunesLibrary);
        for (ITunesTrack track : iTunesLibrary.getTracks().values()) {
            contentDao.addContent(new Content(track));
        }
        log.info(contentDao.getContentList());
    }

    public List<Device> searchDevices() {
        jmdnsGateway.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                jmdnsGateway.close();
            }
        });
        log.info("Device dicovering...");
        jmdnsGateway.waitForDevices();
        List<Device> devices = deviceDao.getDevices();
        log.info(devices);
        return devices;
    }

    public void streamContent(List<Device> devices) {
        if (devices.isEmpty()) {
            log.error("No available devices");
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

        main.parseLibraryXml();
        List<Device> devices = main.searchDevices();
        //main.streamContent(devices);
        main.startTcpServer();
    }
}
