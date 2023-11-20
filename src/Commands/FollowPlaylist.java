package Commands;

import Commands.States.SelectState;
import main.Library;
import main.Playlist;
import main.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FollowPlaylist {
    public LinkedHashMap<String, Object> returnOutput(Library library, HashMap<String, Object> command, SelectState selectState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message = null;

        User user = library.getUser(username);

        if (selectState.selectedType == null) {
            message = "Please select a source before following or unfollowing.";
        } else if (selectState.selectedType.equals("song") || selectState.selectedType.equals("podcast")) {
            message = "The selected source is not a playlist.";
        } else {
            Playlist playlist = selectState.selectedPlaylist;

            if (username.equals(playlist.getOwner())) {
                message = "You cannot follow or unfollow your own playlist.";
            } else if (playlist.visibility.equals("private")) {
                message = "Please select a source before following or unfollowing.";
            } else {
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
