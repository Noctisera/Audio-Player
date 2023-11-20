package Commands;

import main.Library;
import main.Playlist;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ShowPlaylists {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        ArrayList<HashMap<String, Object>> results = new ArrayList<>();

        User user = library.getUser(username);

        for (Playlist playlist : user.getPlaylists()) {
            LinkedHashMap<String, Object> playlistInfo = new LinkedHashMap<>();
            playlistInfo.put("name", playlist.getName());
            playlistInfo.put("songs", playlist.getSongNames());
            playlistInfo.put("visibility", playlist.getVisibility());
            playlistInfo.put("followers", playlist.getFollowers());
            results.add(playlistInfo);
        }


        output.put("command", "showPlaylists");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("result", results);

        return output;
    }
}
