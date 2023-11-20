package Commands;

import Commands.States.LoadState;
import main.Playlist;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class Shuffle {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int seed = -1;
        if (command.get("seed") != null) {
            seed = (int) command.get("seed");
        }
        String message;

        if (loadState.loadedType == null || loadState.remainingTime <= 0) {
            message = "Please load a source before using the shuffle function.";
        } else if (loadState.loadedType.equals("song") || loadState.loadedType.equals("podcast")) {
            message = "The loaded source is not a playlist.";
        } else {
            if (!loadState.shuffleState && seed != -1) {
                loadState.shuffleState = true;

                Random rand = new Random(seed);
                Playlist playlist = new Playlist(loadState.loadedPlaylist);
                Collections.shuffle(playlist.songs, rand);
                loadState.oldPlaylist = loadState.loadedPlaylist;
                loadState.loadedPlaylist = playlist;

                message = "Shuffle function activated successfully.";
            } else {
                loadState.shuffleState = false;

                loadState.oldPlaylist.loopedSong = loadState.loadedPlaylist.loopedSong;
                loadState.oldPlaylist.playingSong = loadState.loadedPlaylist.playingSong;
                loadState.oldPlaylist.followers = loadState.loadedPlaylist.followers;
                loadState.oldPlaylist.visibility = loadState.loadedPlaylist.visibility;

                loadState.loadedPlaylist = loadState.oldPlaylist;
                loadState.oldPlaylist = null;

                message = "Shuffle function deactivated successfully.";
            }
        }

        output.put("command", "shuffle");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
