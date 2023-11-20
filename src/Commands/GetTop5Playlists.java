package Commands;

import main.Library;
import main.Playlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetTop5Playlists {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        int timestamp = (int) command.get("timestamp");
        ArrayList<String> result = new ArrayList<>();

        ArrayList<Playlist> playlists = library.getPlaylists();
        // Sort only by followers
        playlists.sort(Comparator.comparingInt(Playlist::getNumberOfFollowers).reversed());

        for (int i = 0; i < 5 && i < playlists.size(); i++) {
            result.add(playlists.get(i).getName());
        }

        output.put("command", "getTop5Playlists");
        output.put("timestamp", timestamp);
        output.put("result", result);

        return output;
    }
}
