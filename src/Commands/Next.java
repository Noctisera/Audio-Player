package Commands;

import Commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Next {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.loadedType == null) {
            message = "Please load a source before skipping to the next track.";
        } else {
            switch (loadState.loadedType) {
                case "song" -> {
                    loadState.remainingTime = 0;
                    message = "Skipped to next track successfully. The current track is " +
                            loadState.loadedSong.getName() + ".";
                }
                case "playlist" -> {
                    loadState.remainingTime = 0;

                    // Go to the next song in the playlist
                    int nextSongId = loadState.loadedPlaylist.getPlayingSongId() + 1;
                    nextSongId = nextSongId % loadState.loadedPlaylist.getSongs().size();
                    message = "Skipped to next track successfully. The current track is " + loadState.loadedPlaylist.getSongs().get(nextSongId).getName() + ".";
                }
                case "podcast" -> {
                    int remainingEpisodeTime = loadState.remainingTime;
                    Status status = new Status();
                    remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime, loadState.loadedPodcast,
                            loadState.loadedPodcast.getCurrentEpisode(remainingEpisodeTime));

                    loadState.remainingTime -= remainingEpisodeTime;
                    message = "Skipped to next track successfully. The current track is " +
                            loadState.loadedPodcast.getCurrentEpisode(loadState.remainingTime - 1).getName() + ".";
                }
            }
        }

        output.put("command", "next");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
