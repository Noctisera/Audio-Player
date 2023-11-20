package Commands;

import Commands.States.LoadState;
import main.Library;
import main.Playlist;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddRemoveInPlaylist {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int playlistId = (int) command.get("playlistId");
        String message = null;

        if (loadState.loadedType == null) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else {
            switch (loadState.loadedType) {
                case "song" -> {
                    if (playlistId > library.getUser(user).getPlaylists().size()) {
                        message = "The specified playlist does not exist.";
                    } else {
                        Playlist playlist = library.getUser(user).playlists.get(playlistId - 1);
                        if (playlist.getSongs().contains(loadState.loadedSong)) {
                            playlist.removeSong(loadState.loadedSong);
                            message = "Successfully removed from playlist.";
                        } else {
                            playlist.addSong(loadState.loadedSong);
                            message = "Successfully added to playlist.";
                        }
                    }
                }
                case "playlist", "podcast" -> {
                    message = "The loaded source is not a song.";
                }
            }
        }

        output.put("command", "addRemoveInPlaylist");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
