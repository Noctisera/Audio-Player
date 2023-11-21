package commands;

import commands.States.SelectState;
import main.Library;
import main.Playlist;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FollowPlaylist {
    /**
     * Follows or unfollows the selected playlist.
     *
     * @param library    the main library
     * @param command    the command to be executed
     * @param selectState the state of the select command
     * @return the output of the follow command
     */
    public LinkedHashMap<String, Object> returnOutput(final Library library,
                                                      final HashMap<String, Object> command,
                                                      final SelectState selectState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message;

        User user = library.getUser(username);

        if (selectState.getSelectedType() == null) {
            message = "Please select a source before following or unfollowing.";
        } else if (selectState.getSelectedType().equals("song") || selectState.getSelectedType().
                equals("podcast")) {
            message = "The selected source is not a playlist.";
        } else {
            Playlist playlist = selectState.getSelectedPlaylist();

            if (username.equals(playlist.getOwner())) {
                message = "You cannot follow or unfollow your own playlist.";
            } else if (playlist.getVisibility().equals("private")) {
                message = "Please select a source before following or unfollowing.";
            } else {
                assert user != null;
                if (user.getFollowedPlaylists().contains(playlist)) {
                    user.getFollowedPlaylists().remove(playlist);
                    playlist.setFollowers(playlist.getFollowers() - 1);
                    message = "Playlist unfollowed successfully.";
                } else {
                    user.getFollowedPlaylists().add(playlist);
                    playlist.setFollowers(playlist.getFollowers() + 1);
                    message = "Playlist followed successfully.";
                }
            }
        }

        output.put("command", "follow");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
