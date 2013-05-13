package tcp;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.*;
import java.net.Socket;

/**
 * Date: 01.05.13
 * Time: 10:33
 *
 * @author Artem Prigoda
 */
public class TCPPClient {

    private static final String AIRSTAGE_TCP_LOCAL = "_airstage._tcp.local.";

    public static void main(String[] args) throws Exception {

        ServiceInfo serviceInfo;
        final JmDNS mdnsService = JmDNS.create();
        ServiceListener listener = new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent event) {
                ServiceInfo info = event.getDNS().getServiceInfo(event.getType(), event.getName());
            }

            @Override
            public void serviceRemoved(ServiceEvent serviceEvent) {
                // Test service is disappeared.
            }

            @Override
            public void serviceResolved(ServiceEvent event) {
                // Test service info is resolved.
                // serviceURL is usually something like http://192.168.11.2:6666/my-service-name
                ServiceInfo serviceInfo = mdnsService.getServiceInfo(AIRSTAGE_TCP_LOCAL, event.getName());
                System.out.println(serviceInfo);
            }
        };
        mdnsService.addServiceListener(AIRSTAGE_TCP_LOCAL, listener);
        ServiceInfo info = mdnsService.list(AIRSTAGE_TCP_LOCAL)[0];
        System.out.println(info);
        // Retrieve service info from either ServiceInfo[] returned here or listener callback method above.


        Socket socket = new Socket(info.getHostAddress(), info.getPort());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);

            String response = in.readLine();
            System.out.println(response);
        }

        out.close();
        in.close();
        socket.close();
        stdIn.close();

        mdnsService.removeServiceListener("my-service-type", listener);
        mdnsService.close();
    }
}
