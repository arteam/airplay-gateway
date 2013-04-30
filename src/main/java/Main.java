import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import command.PlayCommand;
import command.ScrubCommand;
import gateway.TCPClient;
import itunes.ITunesLibraryParser;
import itunes.data.ITunesLibrary;
import jmdns.JmdnsGateway;
import model.Device;

import javax.inject.Inject;
import java.util.List;

/**
 * Date: 26.04.13
 * Time: 17:41
 *
 * @author Artem Prigoda
 */
public class Main {

    @Inject
    private TCPClient tcpClient;

    @Inject
    private JmdnsGateway jmdnsGateway;

    @Inject
    private ITunesLibraryParser iTunesLibraryParser;

    public void start() {
        jmdnsGateway.start();
        List<Device> devices = jmdnsGateway.getDevices();

        System.out.println(devices);

        Device device = devices.get(0);

        //String url = "ftp://assets:assets@192.168.52.112/mpeg2_mpa/SOUZMULTFILM/Bremenskie_muziikantii.mpg";
        String url = "http://192.168.52.15:8989/SUI/perlaws/samples/sample_iTunes.mov";
        tcpClient.sendCommand(new PlayCommand(url, 0.0), device);

        double position = 0.0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                continue;
            }
            //position += 20.0;
            tcpClient.sendCommand(new ScrubCommand(), device);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                jmdnsGateway.close();
            }
        });
    }


    public void parseLibraryXml() {
        ITunesLibrary iTunesLibrary = iTunesLibraryParser.parseLibrary("./src/main/resources/library.xml");
        System.out.println(iTunesLibrary);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });

        Main main = injector.getInstance(Main.class);
        //main.start();

        main.parseLibraryXml();

    }
}
