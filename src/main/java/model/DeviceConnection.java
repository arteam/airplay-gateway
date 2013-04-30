package model;

import java.io.IOException;
import java.net.Socket;

public class DeviceConnection {

    private Device device;
    private Socket socket;

    public DeviceConnection(Device device, Socket socket) {
        this.device = device;
        this.socket = socket;
    }

    public Device getDevice() {
        return device;
    }

    public Socket getSocket() {
        return socket;
    }


}
