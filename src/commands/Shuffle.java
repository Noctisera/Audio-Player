package commands;

import commands.States.LoadState;
import main.Playlist;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class Shuffle {
    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the shuffle command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int seed = -1;
        if (command.get("seed") != null) {
            seed = (int) command.get("seed");
        }
        String message;

        if (loadState.getLoadedType() == null || loadState.getRemainingTime() <= 0) {
            message = "Please load a source before using the shuffle function.";
        } else if (loadState.getLoadedType().equals("song")
                || loadState.getLoadedType().equals("podcast")) {
            message = "The loaded source is not a playlist.";
        } else {
            if (!(loadState.getShuffleState()) && seed != -1) {
                loadState.setShuffleState(true);

                Random rand = new Random(seed);
                Playlist playlist = new Playlist(loadState.getLoadedPlaylist());
                Collections.shuffle(playlist.getSongs(), rand);
                loadState.setOldPlaylist(loadState.getLoadedPlaylist());
                loadState.setLoadedPlaylist(playlist);

                message = "Shuffle function activated successfully.";
            } else {
                loadState.setShuffleState(false);

                loadState.getOldPlaylist().setLoopedSong(loadState.
                        getLoadedPlaylist().getLoopedSong());
                loadState.getOldPlaylist().setPlayingSong(loadState.
                        getLoadedPlaylist().getPlayingSong());
                loadState.getOldPlaylist().setFollowers(loadState.
                        getLoadedPlaylist().getFollowers());
                loadState.getOldPlaylist().setVisibility(loadState.
                        getLoadedPlaylist().getVisibility());

                loadState.setLoadedPlaylist(loadState.getOldPlaylist());
                loadState.setOldPlaylist(null);

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
