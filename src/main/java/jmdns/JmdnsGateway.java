package jmdns;

import database.DeviceDao;
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
 * Gateway to connection to AIR devices in the network by JMDns
 *
 * @author Artem Prigoda
 */
@Singleton
public class JmdnsGateway {

    private static final String SERVICE_TYPE = "_airplay._tcp.local.";

    @NotNull
    private JmDNS jmDNS;

    @Inject
    private DeviceDao deviceDao;

    /**
     * Listener of event from devices
     */
    private final ServiceListener serviceListener = new ServiceListener() {
        @Override
        public void serviceAdded(ServiceEvent event) {
            // Device has been added. We should call "get service info" for resolving device
            System.out.println(event + " added");
            ServiceInfo info = event.getDNS().getServiceInfo(event.getType(), event.getName());
            System.out.println(info);
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            // Device has been removed. We should update device list
            System.out.println(event + " removed");
            ServiceInfo serviceInfo = jmDNS.getServiceInfo(SERVICE_TYPE, event.getName());
            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            deviceDao.remove(device);
            System.out.println("Removed " + device);
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            // Device resolved. We should add it to device list
            System.out.println(event + " resolved");
            ServiceInfo serviceInfo = jmDNS.getServiceInfo(SERVICE_TYPE, event.getName());

            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            deviceDao.add(device);
            System.out.println("New " + device);
        }
    };

    /**
     * Start device discovering
     */
    public void start() {
        try {
            jmDNS = JmDNS.create();
            jmDNS.addServiceListener(SERVICE_TYPE, serviceListener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close gateway and remove listener
     */
    public void close() {
        try {
            jmDNS.removeServiceListener(SERVICE_TYPE, serviceListener);
            jmDNS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void waitForDevices() {
        ServiceInfo[] list = jmDNS.list(SERVICE_TYPE);
        System.out.println(Arrays.asList(list));
    }

}
