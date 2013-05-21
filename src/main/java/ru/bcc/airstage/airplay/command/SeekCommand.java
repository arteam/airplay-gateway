package ru.bcc.airstage.airplay.command;


import org.jetbrains.annotations.NotNull;

/**
 * Move content to position
 */
public class SeekCommand extends DeviceCommand {

    public SeekCommand(double position) {
        params.put("position", String.valueOf(position));
    }

    @NotNull
    @Override
    public String request() {
        return build("scrub", null);
    }


}