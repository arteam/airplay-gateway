package ru.bcc.airstage.airplay.command;

import org.jetbrains.annotations.NotNull;

/**
 * Play video at certain position
 */
public class PlayCommand extends DeviceCommand {

    @NotNull
    private final String contentURL;
    private final double startPosition;

    public PlayCommand(@NotNull String contentURL, double startPosition) {
        this.contentURL = contentURL;
        this.startPosition = startPosition;
    }

    @NotNull
    @Override
    public String request() {
        String body = String.format("Content-Location: %s" + "\n" +
                "Start-Position: %f\n", contentURL, startPosition);
        return build("play", body);
    }
}
