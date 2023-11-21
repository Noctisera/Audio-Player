package commands;

import commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PlayPause {
    /**
     * Switches the playback state between play and pause.
     *
     * @param loadState the state of the load command
     */
    public void execute(final LoadState loadState) {
        if (loadState.getLoadedType() == null || loadState.getRemainingTime() <= 0) {
            return;
        }

        switch (loadState.getPlaybackState()) {
            case "play" -> {
                loadState.setPlaybackState("pause");
            }
            case "pause" -> {
                loadState.setPlaybackState("play");
            }
            default -> System.out.println("Invalid playback state.");
        }
    }

    /**
     * Returns the output of the playPause command.
     *
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the playPause command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.getLoadedType() == null || loadState.getRemainingTime() <= 0) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else {
            switch (loadState.getPlaybackState()) {
                case "play" -> {
                    message = "Playback resumed successfully.";
                }
                case "pause" -> {
                    message = "Playback paused successfully.";
                }
                default -> System.out.println("Invalid playback state.");
            }
        }

        output.put("command", "playPause");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
