package airplay.command;

import org.jetbrains.annotations.NotNull;

public class StatusCommand extends DeviceCommand {

    @NotNull
    @Override
    public String request() {
        return build("status", null);
    }
}