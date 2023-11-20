package Commands;

import Commands.States.LoadState;
import Commands.States.SelectState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Load {
    public LoadState returnLoad(SelectState selectState, LoadState loadState) {

        if (selectState.selectedType == null) {
            return loadState;
        }

        switch (selectState.selectedType) {
            case "song" -> {
                loadState.loadedSong = selectState.selectedSong;
                loadState.loadedType = "song";
            }
            case "playlist" -> {
                loadState.loadedPlaylist = selectState.selectedPlaylist;
                loadState.loadedType = "playlist";
            }
            case "podcast" -> {
                loadState.loadedPodcast = selectState.selectedPodcast;
                loadState.loadedType = "podcast";
            }
        }

        return loadState;
    }

    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, LoadState loadState,
                                                      SelectState selectState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        if (loadState.loadedType == null || selectState.selectedType == null) {
            message = "Please select a source before attempting to load.";
        } else {
            switch (loadState.loadedType) {
                case "song" -> {
                    message = "Playback loaded successfully.";
                    loadState.remainingTime = loadState.loadedSong.duration;
                    loadState.totalDuration = loadState.loadedSong.duration;
                }
                case "playlist" -> {
                    if (loadState.loadedPlaylist.songs.isEmpty()) {
                        message = "You can't load an empty audio collection!";
                        loadState.Clear();
                    } else {
                        message = "Playback loaded successfully.";
                        loadState.loadedPlaylist.playingSong = loadState.loadedPlaylist.songs.get(0);
                        loadState.remainingTime = loadState.loadedPlaylist.playingSong.getDuration();
                        loadState.totalDuration = loadState.loadedPlaylist.totalDuration();
                    }
                }
                case "podcast" -> {
                    if (loadState.loadedPodcast.episodes.isEmpty()) {
                        message = "You can't load an empty audio collection!";
                        loadState.Clear();
                    } else {
                        message = "Playback loaded successfully.";
                        loadState.remainingTime = loadState.loadedPodcast.totalDuration() -
                                loadState.loadedPodcast.timeWatched;
                        loadState.totalDuration = loadState.loadedPodcast.totalDuration();
                    }
                }
            }

            loadState.lastTimestamp = timestamp;
            loadState.playbackState = "play";
        }


        output.put("command", "load");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
