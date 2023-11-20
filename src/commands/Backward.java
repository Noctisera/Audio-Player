package commands;

import commands.States.LoadState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Backward {
    private static final int TIME_SKIP = 90;

    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the backward command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        String message;

        if (loadState.getLoadedType() == null) {
            message = "Please select a source before rewinding.";
        } else if (!loadState.getLoadedType().equals("podcast")) {
            message = "The loaded source is not a podcast.";
        } else {
            int remainingEpisodeTime = loadState.getRemainingTime();
            Status status = new Status();
            remainingEpisodeTime = status.substituteFromPodcast(remainingEpisodeTime,
                    loadState.getLoadedPodcast(), loadState.getLoadedPodcast().
                            getCurrentEpisode(remainingEpisodeTime));
            int episodeTime = loadState.getLoadedPodcast().getCurrentEpisode(loadState.
                    getRemainingTime()).getDuration();

            int watchedTime = episodeTime - remainingEpisodeTime;

            loadState.setRemainingTime(loadState.getRemainingTime()
                    + Math.min(watchedTime, TIME_SKIP));

            message = "Rewound successfully.";
        }

        output.put("command", "backward");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
