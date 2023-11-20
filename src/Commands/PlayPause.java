package Commands;

import Commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PlayPause {
    public void execute(LoadState loadState) {
        if (loadState.loadedType == null) {
            return;
        }

        switch (loadState.playbackState) {
            case "play" -> {
                loadState.playbackState = "pause";
            }
            case "pause" -> {
                loadState.playbackState = "play";
            }
        }
    }

    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.loadedType == null) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else {
            switch (loadState.playbackState) {
                case "play" -> {
                    message = "Playback resumed successfully.";
                }
                case "pause" -> {
                    message = "Playback paused successfully.";
                }
            }
        }

        output.put("command", "playPause");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
