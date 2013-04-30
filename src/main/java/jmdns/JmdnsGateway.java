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

    @Inject
    private DeviceDao deviceDao;

    public void start() {
        try {
            jmDNS = JmDNS.create();
            jmDNS.addServiceListener(SERVICE_TYPE, serviceListener);
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
            jmDNS.removeServiceListener(SERVICE_TYPE, serviceListener);
            jmDNS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final ServiceListener serviceListener = new ServiceListener() {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println(event + " added");
            ServiceInfo info = event.getDNS().getServiceInfo(event.getType(), event.getName());
            System.out.println(info);
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println(event + " removed");
            List<Device> devices = getDevices();
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println(event + " resolved");
            ServiceInfo serviceInfo = jmDNS.getServiceInfo(SERVICE_TYPE, event.getName());

            Device device = new Device(serviceInfo.getName(), serviceInfo.getInetAddress(), serviceInfo.getPort());
            System.out.println("New " + device);
            deviceDao.add(device);
        }
    };
}
