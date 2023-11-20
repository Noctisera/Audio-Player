package Commands;

import main.Library;
import main.Song;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ShowPrefferedSongs {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        User user = library.getUser(username);
        ArrayList<String> result = new ArrayList<>();

        for (Song song : user.getLikedSongs()) {
            result.add(song.getName());
        }

        output.put("command", "showPreferredSongs");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("result", result);

        return output;
    }
}
