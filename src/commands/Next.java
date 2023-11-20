package commands;

import commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Next {
    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the next command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.getLoadedType() == null) {
            message = "Please load a source before skipping to the next track.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    loadState.setRemainingTime(0);
                    message = "Skipped to next track successfully. The current track is "
                            + loadState.getLoadedSong().getName() + ".";
                }
                case "playlist" -> {
                    loadState.setRemainingTime(0);

                    int nextSongId = loadState.getLoadedPlaylist().getPlayingSongId() + 1;
                    nextSongId = nextSongId % loadState.getLoadedPlaylist().getSongs().size();
                    message = "Skipped to next track successfully. The current track is "
                            + loadState.getLoadedPlaylist().getSongs().get(nextSongId).getName()
                            + ".";
                }
                case "podcast" -> {
                    int remainingEpisodeTime = loadState.getRemainingTime();
                    Status status = new Status();
                    remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime,
                            loadState.getLoadedPodcast(), loadState.getLoadedPodcast().
                                    getCurrentEpisode(remainingEpisodeTime));

                    loadState.setRemainingTime(loadState.getRemainingTime()
                            - remainingEpisodeTime);
                    message = "Skipped to next track successfully. The current track is "
                            + loadState.getLoadedPodcast().getCurrentEpisode(loadState.
                            getRemainingTime() - 1).getName() + ".";
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        output.put("command", "next");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
