package ru.bcc.airstage;

import com.google.inject.*;
import com.google.inject.name.Names;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import ru.bcc.airstage.airplay.AirPlayGateway;
import ru.bcc.airstage.airplay.ConnectionPool;
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
import ru.bcc.airstage.server.ContentPlayer;
import ru.bcc.airstage.server.TCPServer;
import ru.bcc.airstage.stream.StreamServer;

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

    @Inject
    private AirPlayGateway airPlayGateway;

    @Inject
    private JmdnsGateway jmdnsGateway;

    @Inject
    private ITunesLibraryProvider iTunesLibraryProvider;

    @Inject
    private ContentDao contentDao;

    @Inject
    private DeviceDao deviceDao;

    @Inject
    private TCPServer tcpServer;

    @Inject
    private StreamServer streamServer;

    @Inject
    private ContentPlayer contentPlayer;

    @Inject
    private ConnectionPool connectionPool;

    @Inject
    private Housekeeping housekeeping;

    public void parseLibraryXml() {
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get("./library.xml");
        //ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get();
        for (ITunesTrack track : iTunesLibrary.getTracks().values()) {
            contentDao.addContent(new Content(track));
        }
        log.info("Found content: " + contentDao.getContentList());
    }

    public void searchDevices() {
        log.info("Device discovering...");
        jmdnsGateway.start();
        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                jmdnsGateway.close();
            }
        });
    }

    public void startTcpServer() {
        tcpServer.start();
        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                tcpServer.stop();
            }
        });
    }

    public void startStreamServer() {
        streamServer.start();

        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                streamServer.stop();
            }
        });
    }

    private void stopPlayerAfterShutdown() {
        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                contentPlayer.stop();
            }
        });
    }

    private void closeAirPlayConnectionsAfterShutdown() {
        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                connectionPool.closeAll();
            }
        });
    }

    public void streamContent() {
        List<Device> devices;
        while (true) {
            devices = deviceDao.getDevices();
            if (!devices.isEmpty()) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        Device device = devices.get(0);

        //String url = "ftp://assets:assets@192.168.52.112/mpeg2_mpa/SOUZMULTFILM/Bremenskie_muziikantii.mpg";
        //String url = "http://192.168.52.15:8989/SUI/perlaws/samples/sample_iTunes.mov";
        String url = "http://192.168.52.248:8080/stream?code=5456";
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


    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("src", true, "Source of the xls file");
        options.addOption("dest", true, "Destination of the xls file");
        CommandLine parser = new GnuParser().parse(options, args);

        final int clientPort = Integer.parseInt(parser.getOptionValue("clientPort", "9099"));
        final int streamPort = Integer.parseInt(parser.getOptionValue("streamPort", "8080"));

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bindConstant().annotatedWith(Names.named("clientPort")).to(clientPort);
                bindConstant().annotatedWith(Names.named("streamPort")).to(streamPort);
            }
        });

        Main main = injector.getInstance(Main.class);


        main.startTcpServer();
        main.startStreamServer();
        main.parseLibraryXml();
        main.searchDevices();

        main.stopPlayerAfterShutdown();
        main.closeAirPlayConnectionsAfterShutdown();
        //main.streamContent();
    }
}
