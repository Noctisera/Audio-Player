package commands;

import commands.States.LoadState;
import main.Library;
import main.Song;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Like {
    /**
     * Like the loaded Song or the playing Song from the loaded Playlist.
     *
     * @param library   the library of the application
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the like command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        User user = library.getUser(username);

        if (loadState.getLoadedType() == null || loadState.getRemainingTime() <= 0) {
            message = "Please load a source before liking or unliking.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    assert user != null;
                    if (user.getLikedSongs().contains(loadState.getLoadedSong())) {
                        user.getLikedSongs().remove(loadState.getLoadedSong());
                        message = "Unlike registered successfully.";
                        loadState.getLoadedSong().setLikes(loadState.getLoadedSong().
                                getNumberOfLikes() - 1);
                    } else {
                        user.getLikedSongs().add(loadState.getLoadedSong());
                        message = "Like registered successfully.";
                        loadState.getLoadedSong().setLikes(loadState.getLoadedSong().
                                getNumberOfLikes() + 1);
                    }
                }
                case "playlist" -> {
                    Song currentSong = loadState.getLoadedPlaylist().getPlayingSong();

                    if (user.getLikedSongs().contains(currentSong)) {
                        user.getLikedSongs().remove(currentSong);
                        message = "Unlike registered successfully.";
                        currentSong.setLikes(currentSong.getNumberOfLikes() - 1);
                    } else {
                        user.getLikedSongs().add(currentSong);
                        message = "Like registered successfully.";
                        currentSong.setLikes(currentSong.getNumberOfLikes() + 1);
                    }
                }
                case "podcast" -> {
                    message = "Loaded source is not a song.";
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        output.put("command", "like");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
