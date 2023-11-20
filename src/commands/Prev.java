package commands;

import commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Prev {
    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the prev command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.getLoadedType() == null) {
            message = "Please load a source before returning to the previous track.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    loadState.setRemainingTime(loadState.getLoadedSong().getDuration());
                    message = "Returned to previous track successfully. The current track is "
                            + loadState.getLoadedSong().getName() + ".";
                }
                case "playlist" -> {
                    int playingSongId = loadState.getLoadedPlaylist().getPlayingSongId();
                    if (loadState.getRemainingTime() + 1 >= loadState.getLoadedPlaylist().
                            getSongs().get(playingSongId).getDuration()) {
                        playingSongId = (playingSongId - 1 + loadState.getLoadedPlaylist().
                                getSongs().size()) % loadState.getLoadedPlaylist().
                                getSongs().size();
                        loadState.setRemainingTime(loadState.getLoadedPlaylist().getSongs().
                                get(playingSongId).getDuration());
                        loadState.getLoadedPlaylist().setPlayingSong(loadState.getLoadedPlaylist().
                                getSongs().get(playingSongId));
                    } else {
                        loadState.setRemainingTime(loadState.getLoadedPlaylist().getSongs().
                                get(playingSongId).getDuration());
                    }
                    message = "Returned to previous track successfully. The current track is "
                            + loadState.getLoadedPlaylist().getSongs().get(playingSongId).getName()
                            + ".";
                }
                case "podcast" -> {
                    int remainingEpisodeTime = loadState.getRemainingTime();
                    Status status = new Status();
                    remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime,
                            loadState.getLoadedPodcast(), loadState.getLoadedPodcast().
                                    getCurrentEpisode(remainingEpisodeTime));
                    int watchedEpisodeTime = loadState.getLoadedPodcast().getCurrentEpisode(
                            remainingEpisodeTime).getDuration() - remainingEpisodeTime;

                    loadState.setRemainingTime(loadState.getRemainingTime() + watchedEpisodeTime);
                    message = "Skipped to next track successfully. The current track is "
                            + loadState.getLoadedPodcast().getCurrentEpisode(loadState.
                            getRemainingTime()).getName() + ".";
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        output.put("command", "prev");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
