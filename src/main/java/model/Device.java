package model;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;

public class Device {

    @Nullable
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final InetAddress address;

    private final int port;

    public Device(@NotNull String name, @NotNull InetAddress address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(address.getAddress());
            messageDigest.update(String.valueOf(port).getBytes("UTF-8"));
            id = String.format("%032X", new BigInteger(1, messageDigest.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Device{" + "id=" + id + ", name=" + name +
                ", address=" + address + ", port=" + port + '}';
    }
}
