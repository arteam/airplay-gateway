package com.github.arteam;

import com.google.inject.*;
import com.google.inject.name.Names;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import com.github.arteam.airplay.AirPlayGateway;
import com.github.arteam.airplay.ConnectionPool;
import com.github.arteam.airplay.command.PlayCommand;
import com.github.arteam.airplay.command.ScrubCommand;
import com.github.arteam.database.ContentDao;
import com.github.arteam.database.DeviceDao;
import com.github.arteam.itunes.ITunesLibraryProvider;
import com.github.arteam.itunes.data.ITunesLibrary;
import com.github.arteam.itunes.data.ITunesTrack;
import com.github.arteam.itunes.watch.ITunesLibraryWatcher;
import com.github.arteam.jmdns.JmdnsGateway;
import com.github.arteam.model.Content;
import com.github.arteam.model.Device;
import com.github.arteam.server.ContentPlayer;
import com.github.arteam.server.TCPServer;
import com.github.arteam.stream.StreamServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ITunesLibraryWatcher iTunesLibraryWatcher;

    @Inject
    private Housekeeping housekeeping;

    public void parseLibraryXml() {
        ITunesLibrary iTunesLibrary = iTunesLibraryProvider.get();
        Map<String, Content> contentMap = new HashMap<String, Content>();
        for (ITunesTrack track : iTunesLibrary.getTracks().values()) {
            Content content = new Content(track);
            contentMap.put(content.getId(), content);
        }
        contentDao.setContent(contentMap);
        log.info(contentDao.toString());

    }

    public void searchDevices() {
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

    private void startMonitoringITunesLibrary() {
        iTunesLibraryWatcher.start();
        housekeeping.afterShutdown(new Runnable() {
            @Override
            public void run() {
                iTunesLibraryWatcher.stop();
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
        main.startMonitoringITunesLibrary();
        main.searchDevices();

        main.stopPlayerAfterShutdown();
        main.closeAirPlayConnectionsAfterShutdown();
        log.info("AirStage started");
        //main.streamContent();
    }
}
