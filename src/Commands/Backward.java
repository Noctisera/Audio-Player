package Commands;

import Commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Backward {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command,
                                                      LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message;

        if (loadState.loadedType == null) {
            message = "Please select a source before rewinding.";
        } else if (!loadState.loadedType.equals("podcast")) {
            message = "The loaded source is not a podcast.";
        } else {
            int remainingEpisodeTime = loadState.remainingTime;
            Status status = new Status();
            remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime, loadState.loadedPodcast,
                    loadState.loadedPodcast.getCurrentEpisode(remainingEpisodeTime));
            int episodeTime = loadState.loadedPodcast.getCurrentEpisode(loadState.remainingTime).getDuration();

            int watchedTime = episodeTime - remainingEpisodeTime;

            loadState.remainingTime += Math.min(watchedTime, 90);

            message = "Rewound successfully.";
        }

        output.put("command", "backward");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
