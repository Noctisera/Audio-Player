package Commands;

import Commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Prev {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        if (loadState.loadedType == null) {
            message = "Please load a source before returning to the previous track.";
        } else {
            switch (loadState.loadedType) {
                case "song" -> {
                    loadState.remainingTime = loadState.loadedSong.getDuration();
                    message = "Returned to previous track successfully. The current track is " +
                            loadState.loadedSong.getName() + ".";
                }
                case "playlist" -> {
                    int playingSongId = loadState.loadedPlaylist.getPlayingSongId();
                    if (loadState.remainingTime + 1 >= loadState.loadedPlaylist.getSongs().get(playingSongId).getDuration()) {
                        playingSongId = (playingSongId - 1 + loadState.loadedPlaylist.getSongs().size()) % loadState.loadedPlaylist.getSongs().size();
                        loadState.remainingTime = loadState.loadedPlaylist.getSongs().get(playingSongId).getDuration();
                        loadState.loadedPlaylist.playingSong = loadState.loadedPlaylist.getSongs().get(playingSongId);
                        message = "Returned to previous track successfully. The current track is " +
                                loadState.loadedPlaylist.getSongs().get(playingSongId).getName() + ".";
                    } else {
                        loadState.remainingTime = loadState.loadedPlaylist.getSongs().get(playingSongId).getDuration();
                        message = "Returned to previous track successfully. The current track is " +
                                loadState.loadedPlaylist.getSongs().get(playingSongId).getName() + ".";
                    }
                }
                case "podcast" -> {
                    int remainingEpisodeTime = loadState.remainingTime;
                    Status status = new Status();
                    remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime, loadState.loadedPodcast,
                            loadState.loadedPodcast.getCurrentEpisode(remainingEpisodeTime));
                    int watchedEpisodeTime = loadState.loadedPodcast.getCurrentEpisode(remainingEpisodeTime).getDuration() - remainingEpisodeTime;

                    loadState.remainingTime += watchedEpisodeTime;
                    message = "Skipped to next track successfully. The current track is " +
                            loadState.loadedPodcast.getCurrentEpisode(loadState.remainingTime).getName() + ".";
                }
            }
        }

        output.put("command", "prev");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
