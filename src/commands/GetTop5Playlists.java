package commands;

import main.Library;
import main.Playlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetTop5Playlists {
    private static final int MAX_PLAYLISTS = 5;
    /**
     * Get the 5 most followed playlists from the library.
     *
     * @param library the library of the application
     * @param command the command to be executed
     * @return the output of the getTop5Playlists command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        int timestamp = (int) command.get("timestamp");
        ArrayList<String> result = new ArrayList<>();

        ArrayList<Playlist> playlists = library.getPlaylists();
        playlists.sort(Comparator.comparingInt(Playlist::getNumberOfFollowers).reversed());

        for (int i = 0; i < MAX_PLAYLISTS && i < playlists.size(); i++) {
            result.add(playlists.get(i).getName());
        }

        output.put("command", "getTop5Playlists");
        output.put("timestamp", timestamp);
        output.put("result", result);

        return output;
    }
}
