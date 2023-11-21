package commands;

import main.Library;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SwitchVisibility {
    /**
     * @param library the main library
     * @param command the command to be executed
     * @return the output of the switchVisibility command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int playlistId = (int) command.get("playlistId");
        String message;

        User user = library.getUser(username);

        assert user != null;
        if (playlistId > user.getPlaylists().size()) {
            message = "The specified playlist ID is too high.";
        } else {
            playlistId--;
            if (user.getPlaylists().get(playlistId).getVisibility().equals("private")) {
                user.getPlaylists().get(playlistId).setVisibility("public");
                message = "Visibility status updated successfully to public.";
            } else {
                user.getPlaylists().get(playlistId).setVisibility("private");
                message = "Visibility status updated successfully to private.";
            }
        }

        output.put("command", "switchVisibility");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
