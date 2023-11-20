package Commands;

import Commands.States.LoadState;
import main.Library;
import main.Song;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Like {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        User user = library.getUser(username);

        if (loadState.loadedType == null) {
            message = "Please load a source before liking or unliking.";
        } else {
            switch (loadState.loadedType) {
                case "song" -> {
                    if (user.getLikedSongs().contains(loadState.loadedSong)) {
                        user.getLikedSongs().remove(loadState.loadedSong);
                        message = "Unlike registered successfully.";
                        loadState.loadedSong.setLikes(loadState.loadedSong.getNumberOfLikes() - 1);
                    } else {
                        user.getLikedSongs().add(loadState.loadedSong);
                        message = "Like registered successfully.";
                        loadState.loadedSong.setLikes(loadState.loadedSong.getNumberOfLikes() + 1);
                    }
                }
                case "playlist" -> {
                    Song currentSong = loadState.loadedPlaylist.playingSong;

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
            }
        }

        output.put("command", "like");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
