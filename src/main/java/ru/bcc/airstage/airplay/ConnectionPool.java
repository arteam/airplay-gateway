package ru.bcc.airstage.airplay;

import ru.bcc.airstage.model.Device;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Singleton;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 26.04.13
 * Time: 21:14
 * Pool of connections to AirPlay devices
 *
 * @author Artem Prigoda
 */
@Singleton
public class ConnectionPool {

    @NotNull
    private final Map<Device, Socket> connections = new ConcurrentHashMap<Device, Socket>();

    @NotNull
    public Socket get(@NotNull Device device) {
        Socket socket = connections.get(device);
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket(device.getAddress(), device.getPort());
                connections.put(device, socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return socket;
    }

    @NotNull
    public Socket remove(@NotNull Device device) {
        return connections.remove(device);
    }

    public void closeAll() {
        for (Socket socket : connections.values()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Unable close " + socket);
            }
        }
    }
}
