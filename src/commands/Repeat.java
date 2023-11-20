package commands;

import commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Repeat {
    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the repeat command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = (String) command.get("title");

        if (loadState.getLoadedType() == null) {
            message = "Please load a source before setting the repeat status.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song", "podcast" -> {
                    switch (loadState.getRepeatState()) {
                        case "No Repeat" -> {
                            loadState.setRepeatState("Repeat Once");
                            message = "Repeat mode changed to repeat once.";
                        }
                        case "Repeat Once" -> {
                            loadState.setRepeatState("Repeat Infinite");
                            message = "Repeat mode changed to repeat infinite.";
                        }
                        case "Repeat Infinite" -> {
                            loadState.setRepeatState("No Repeat");
                            message = "Repeat mode changed to no repeat.";
                        }
                        default -> System.out.println("Invalid repeat state.");
                    }
                }
                case "playlist" -> {
                    switch (loadState.getRepeatState()) {
                        case "No Repeat" -> {
                            loadState.setRepeatState("Repeat All");
                            message = "Repeat mode changed to repeat all.";
                        }
                        case "Repeat All" -> {
                            loadState.setRepeatState("Repeat Current Song");
                            message = "Repeat mode changed to repeat current song.";

                            loadState.getLoadedPlaylist().setLoopedSong(loadState.
                                    getLoadedPlaylist().getPlayingSong());

                            loadState.setTotalDuration(loadState.getLoadedPlaylist().
                                    getLoopedSong().getDuration());
                        }
                        case "Repeat Current Song" -> {
                            loadState.setRepeatState("No Repeat");
                            message = "Repeat mode changed to no repeat.";

                            loadState.setTotalDuration(loadState.getLoadedPlaylist().
                                    totalDuration());
                        }
                        default -> System.out.println("Invalid repeat state.");
                    }
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        output.put("command", "repeat");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
