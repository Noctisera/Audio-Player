package commands;

import commands.States.LoadState;
import main.Library;
import main.Playlist;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddRemoveInPlaylist {
    /**
     * @param library   the library of the application
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the addRemoveInPlaylist command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int playlistId = (int) command.get("playlistId");
        String message = null;

        if (loadState.getLoadedType() == null) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else {
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    if (playlistId > library.getUser(user).getPlaylists().size()) {
                        message = "The specified playlist does not exist.";
                    } else {
                        Playlist playlist = library.getUser(user).getPlaylists().
                                get(playlistId - 1);
                        if (playlist.getSongs().contains(loadState.getLoadedSong())) {
                            playlist.removeSong(loadState.getLoadedSong());
                            message = "Successfully removed from playlist.";
                        } else {
                            playlist.addSong(loadState.getLoadedSong());
                            message = "Successfully added to playlist.";
                        }
                    }
                }
                case "playlist", "podcast" -> {
                    message = "The loaded source is not a song.";
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        output.put("command", "addRemoveInPlaylist");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
