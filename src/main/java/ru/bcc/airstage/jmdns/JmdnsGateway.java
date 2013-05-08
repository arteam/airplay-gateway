package ru.bcc.airstage.jmdns;

import ru.bcc.airstage.database.DeviceDao;
import ru.bcc.airstage.model.Device;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import com.google.inject.*;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.util.Arrays;

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
    private static final Logger log = Logger.getLogger(JmdnsGateway.class);

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
            log.info(event + " added");
            ServiceInfo info = event.getDNS().getServiceInfo(event.getType(), event.getName());
            log.info(info);
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            // Device has been removed. We should update device list
            log.info(event + " removed");
            ServiceInfo serviceInfo = jmDNS.getServiceInfo(SERVICE_TYPE, event.getName());
            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            deviceDao.remove(device);
            log.info("Removed " + device);
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            // Device resolved. We should add it to device list
            log.info(event + " resolved");
            ServiceInfo serviceInfo = jmDNS.getServiceInfo(SERVICE_TYPE, event.getName());

            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            deviceDao.add(device);
            log.info("New " + device);
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
            log.info("Closing JmDNS gateway...");
            jmDNS.removeServiceListener(SERVICE_TYPE, serviceListener);
            jmDNS.close();
            log.info("JmDNS gateway is closed");
        } catch (IOException e) {
            log.error("I/O error", e);
        }
    }


    public void waitForDevices() {
        ServiceInfo[] list = jmDNS.list(SERVICE_TYPE);
        log.info("Service info: " + Arrays.asList(list));
    }

}
