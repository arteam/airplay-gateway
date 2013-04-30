package command;


import org.jetbrains.annotations.NotNull;

/**
 * Get current status of the playback
 */
public class ScrubCommand extends DeviceCommand {

    public ScrubCommand() {
    }

    public ScrubCommand(double position) {
        params.put("position", String.valueOf(position));
    }

    @Override
    @NotNull
    protected String requestType() {
        return "GET";
    }

    @NotNull
    @Override
    public String request() {
        return build("scrub", null);
    }


}