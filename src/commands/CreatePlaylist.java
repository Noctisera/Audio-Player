package commands;

import main.Library;
import main.Playlist;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreatePlaylist {
    /**
     * @param library the main library
     * @param command the command to be executed
     * @return the output of the createPlaylist command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String playlistName = (String) command.get("playlistName");
        String message = null;

        User user = library.getUser(username);

        assert user != null;
        for (Playlist playlist : user.getPlaylists()) {
            if (playlist.getName().equals(playlistName)) {
                message = "A playlist with the same name already exists.";

                break;
            }
        }

        if (message == null) {
            Playlist newPlaylist = new Playlist(playlistName, username);
            user.getPlaylists().add(newPlaylist);
            library.getPlaylists().add(newPlaylist);
            message = "Playlist created successfully.";
        }

        output.put("command", "createPlaylist");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
