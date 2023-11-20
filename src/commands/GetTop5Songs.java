package commands;

import main.Library;
import main.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetTop5Songs {
    private static final int MAX_SONGS = 5;
    /**
     * @param library the library of the application
     * @param command the command to be executed
     * @return the output of the getTop5Songs command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        int timestamp = (int) command.get("timestamp");
        ArrayList<String> result = new ArrayList<>();

        ArrayList<Song> songs = library.getSongs();
        songs.sort(Comparator.comparingInt(Song::getNumberOfLikes).reversed());

        for (int i = 0; i < MAX_SONGS && i < songs.size(); i++) {
            result.add(songs.get(i).getName());
        }

        output.put("command", "getTop5Songs");
        output.put("timestamp", timestamp);
        output.put("result", result);

        return output;
    }
}
