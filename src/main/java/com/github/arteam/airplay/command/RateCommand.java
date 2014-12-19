package com.github.arteam.airplay.command;

import org.jetbrains.annotations.NotNull;

public class RateCommand extends DeviceCommand {

    public RateCommand(double rate) {
        params.put("value", String.valueOf(rate));
    }

    @NotNull
    @Override
    public String request() {
        return build("rate", null);
    }
}