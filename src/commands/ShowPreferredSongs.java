package commands;

import main.Library;
import main.Song;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ShowPreferredSongs {
    /**
     * Returns the data of the liked songs by the user.
     *
     * @param library the library of the application
     * @param command the command to be executed
     * @return the output of the showPreferredSongs command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command) {
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
