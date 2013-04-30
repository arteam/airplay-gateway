package jmdns;

import model.Device;
import org.jetbrains.annotations.NotNull;

import com.google.inject.*;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 29.04.13
 * Time: 11:46
 *
 * @author Artem Prigoda
 */
@Singleton
public class JmdnsGateway {

    private static final String SERVICE_TYPE = "_airplay._tcp.local.";

    @NotNull
    private JmDNS jmDNS;

    public void start() {
        try {
            jmDNS = JmDNS.create();
            jmDNS.addServiceListener(SERVICE_TYPE, SERVICE_LISTENER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public List<Device> getDevices() {
        ServiceInfo[] list = jmDNS.list(SERVICE_TYPE);
        List<Device> devices = new ArrayList<Device>();
        for (ServiceInfo serviceInfo : list) {
            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            devices.add(device);
        }
        return devices;
    }

    public void close() {
        try {
            jmDNS.removeServiceListener(SERVICE_TYPE, SERVICE_LISTENER);
            jmDNS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ServiceListener SERVICE_LISTENER = new ServiceListener() {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println(event + " added");
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println(event + " removed");
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println(event + " resolved");
        }
    };
}
