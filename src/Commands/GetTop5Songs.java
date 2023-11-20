package Commands;

import main.Library;
import main.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetTop5Songs {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        int timestamp = (int) command.get("timestamp");
        ArrayList<String> result = new ArrayList<>();

        ArrayList<Song> songs = library.getSongs();
        // Sort only by likes
        songs.sort(Comparator.comparingInt(Song::getNumberOfLikes).reversed());

        for (int i = 0; i < 5 && i < songs.size(); i++) {
            result.add(songs.get(i).getName());
        }

        output.put("command", "getTop5Songs");
        output.put("timestamp", timestamp);
        output.put("result", result);

        return output;
    }
}
