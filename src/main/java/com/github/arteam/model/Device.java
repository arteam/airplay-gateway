package com.github.arteam.model;


import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;

/**
 * Date: 30.04.13
 * Time: 12:11
 * Represents an AIR device in the network
 *
 * @author Artem Prigoda
 */
public class Device {

    /**
     * Generated id of the device
     */
    @NotNull
    private String id;

    /**
     * Specified device name
     */
    @NotNull
    private String name;

    /**
     * Actual ip address of the device
     */
    @NotNull
    private InetAddress address;

    /**
     * Client TCP-port of the device
     */
    private int port;

    public Device(@NotNull String id, @NotNull String name, @NotNull InetAddress address, int port) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public Device(@NotNull String name, @NotNull InetAddress address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
        id = md5(address, port);
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

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setAddress(@NotNull InetAddress address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private static String md5(InetAddress address, int port) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(address.getAddress());
            messageDigest.update(String.valueOf(port).getBytes("UTF-8"));
            return String.format("%032X", new BigInteger(1, messageDigest.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Device{" + "id=" + id + ", name=" + name +
                ", address=" + address + ", port=" + port + '}';
    }
}
