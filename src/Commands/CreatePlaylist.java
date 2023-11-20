package Commands;

import Commands.States.LoadState;
import main.Library;
import main.Playlist;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreatePlaylist {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String playlistName = (String) command.get("playlistName");
        String message = null;

        User user = library.getUser(username);

        for (Playlist playlist : user.playlists) {
            if (playlist.name.equals(playlistName)) {
                message = "A playlist with the same name already exists.";

                break;
            }
        }

        if (message == null) {
            Playlist newPlaylist = new Playlist(playlistName, username);
            user.playlists.add(newPlaylist);
            library.playlists.add(newPlaylist);
            message = "Playlist created successfully.";
        }

        output.put("command", "createPlaylist");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
