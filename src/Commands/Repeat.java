package Commands;

import Commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Repeat {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = (String) command.get("title");

        if (loadState.loadedType == null) {
            message = "Please load a source before setting the repeat status.";
        } else {
            switch (loadState.loadedType) {
                case "song", "podcast" -> {
                    switch (loadState.repeatState) {
                        case "No Repeat" -> {
                            loadState.repeatState = "Repeat Once";
                            message = "Repeat mode changed to repeat once.";
                        }
                        case "Repeat Once" -> {
                            loadState.repeatState = "Repeat Infinite";
                            message = "Repeat mode changed to repeat infinite.";
                        }
                        case "Repeat Infinite" -> {
                            loadState.repeatState = "No Repeat";
                            message = "Repeat mode changed to no repeat.";
                        }
                    }
                }
                case "playlist" -> {
                    switch (loadState.repeatState) {
                        case "No Repeat" -> {
                            loadState.repeatState = "Repeat All";
                            message = "Repeat mode changed to repeat all.";
                        }
                        case "Repeat All" -> {
                            loadState.repeatState = "Repeat Current Song";
                            message = "Repeat mode changed to repeat current song.";

                            loadState.loadedPlaylist.loopedSong = loadState.loadedPlaylist.playingSong;

                            loadState.totalDuration = loadState.loadedPlaylist.loopedSong.duration;
                        }
                        case "Repeat Current Song" -> {
                            loadState.repeatState = "No Repeat";
                            message = "Repeat mode changed to no repeat.";

                            loadState.totalDuration = loadState.loadedPlaylist.totalDuration();
                        }
                    }
                }
            }
        }


        output.put("command", "repeat");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
