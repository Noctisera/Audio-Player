package commands;

import commands.States.LoadState;
import commands.States.SelectState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Load {
    /**
     * Loads the selected track into the loadState.
     *
     * @param selectState the state of the select command
     * @param loadState   the state of the load command
     * @return the output of the load command
     */
    public LoadState returnLoad(final SelectState selectState, final LoadState loadState) {
        if (selectState.getSelectedType() == null) {
            return loadState;
        }

        switch (selectState.getSelectedType()) {
            case "song" -> {
                loadState.setLoadedSong(selectState.getSelectedSong());
                loadState.setLoadedType("song");
            }
            case "playlist" -> {
                loadState.setLoadedPlaylist(selectState.getSelectedPlaylist());
                loadState.setLoadedType("playlist");
            }
            case "podcast" -> {
                loadState.setLoadedPodcast(selectState.getSelectedPodcast());
                loadState.setLoadedType("podcast");
            }
            default -> System.out.println("Invalid select type.");
        }

        return loadState;
    }

    /**
     * @param command     the command to be executed
     * @param loadState   the state of the load command
     * @param selectState the state of the select command
     * @return the output of the load command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState,
                                                      final SelectState selectState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        if (loadState.getLoadedType() == null || selectState.getSelectedType() == null) {
            message = "Please select a source before attempting to load.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    message = "Playback loaded successfully.";
                    loadState.setRemainingTime(loadState.getLoadedSong().getDuration());
                    loadState.setTotalDuration(loadState.getLoadedSong().getDuration());
                }
                case "playlist" -> {
                    if (loadState.getLoadedPlaylist().getSongs().isEmpty()) {
                        message = "You can't load an empty audio collection!";
                        loadState.clear();
                    } else {
                        message = "Playback loaded successfully.";
                        loadState.getLoadedPlaylist().setPlayingSong(loadState.getLoadedPlaylist().
                                getSongs().get(0));
                        loadState.setRemainingTime(loadState.getLoadedPlaylist().getPlayingSong().
                                getDuration());
                        loadState.setTotalDuration(loadState.getLoadedPlaylist().totalDuration());
                    }
                }
                case "podcast" -> {
                    if (loadState.getLoadedPodcast().getEpisodes().isEmpty()) {
                        message = "You can't load an empty audio collection!";
                        loadState.clear();
                    } else {
                        message = "Playback loaded successfully.";
                        loadState.setRemainingTime(loadState.getLoadedPodcast().totalDuration()
                                - loadState.getLoadedPodcast().getTimeWatched());
                        loadState.setTotalDuration(loadState.getLoadedPodcast().totalDuration());
                    }
                }
                default -> System.out.println("Invalid load type.");
            }

            loadState.setLastTimestamp(timestamp);
            loadState.setPlaybackState("play");
        }

        output.put("command", "load");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
