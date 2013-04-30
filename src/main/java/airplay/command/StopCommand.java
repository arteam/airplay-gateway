package airplay.command;

import org.jetbrains.annotations.NotNull;

public class StopCommand extends DeviceCommand {
    @NotNull
    @Override
    public String request() {
        return build("stop", null);
    }
}