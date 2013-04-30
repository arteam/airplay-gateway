package database;

import com.google.inject.Singleton;
import model.Device;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Date: 30.04.13
 * Time: 15:34
 * DAO for work with devices
 *
 * @author Artem Prigoda
 */
@Singleton
public class DeviceDao {

    private final List<Device> devices;

    public DeviceDao() {
        devices = new CopyOnWriteArrayList<Device>();
    }

    public void add(Device device) {
        devices.add(device);
    }

    public void remove(Device device) {
        devices.remove(device);
    }

    public List<Device> getDevices() {
        return devices;
    }
}
