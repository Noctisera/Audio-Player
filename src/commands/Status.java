package commands;

import commands.States.LoadState;
import main.Episode;
import main.Podcast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Status {
    /**
     * @param command   the command to be executed
     * @param loadState the state of the load command
     * @return the output of the next command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        LinkedHashMap<String, Object> stats = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        String name = null;
        int remainedTime = 0;
        String repeat = loadState.getRepeatState();
        boolean shuffle = loadState.getShuffleState();
        boolean paused = loadState.getPlaybackState().equals("pause");

        if (loadState.getLoadedType() == null || loadState.getRemainingTime() <= 0) {
            name = "";
            paused = true;
        } else {
            remainedTime = loadState.getRemainingTime();
            switch (loadState.getLoadedType()) {
                case "song" -> {
                    name = loadState.getLoadedSong().getName();
                }
                case "playlist" -> {
                    if (loadState.getRepeatState().equals("Repeat Current Song")) {
                        name = loadState.getLoadedPlaylist().getLoopedSong().getName();
                    } else {
                        name = loadState.getLoadedPlaylist().getPlayingSong().getName();
                    }
                }
                case "podcast" -> {
                    name = Objects.requireNonNull(loadState.getLoadedPodcast().getCurrentEpisode(
                            loadState.getRemainingTime())).getName();
                    remainedTime = substituteFromPodcast(remainedTime,
                            loadState.getLoadedPodcast(), loadState.getLoadedPodcast().
                                    getCurrentEpisode(loadState.getRemainingTime()));
                }
                default -> System.out.println("Invalid load type.");
            }
        }

        stats.put("name", name);
        stats.put("remainedTime", remainedTime);
        stats.put("repeat", repeat);
        stats.put("shuffle", shuffle);
        stats.put("paused", paused);

        output.put("command", "status");
        output.put("user", user);
        output.put("timestamp", timestamp);
        output.put("stats", stats);

        return output;
    }

    /**
     * @param remainedTime   the remaining time of the podcast
     * @param podcast        the podcast to be substituted
     * @param currentEpisode the current episode of the podcast
     * @return the remaining time of the podcast
     */
    public int substituteFromPodcast(final int remainedTime, final Podcast podcast,
                                     final Episode currentEpisode) {
        int newRemainedTime = remainedTime;
        int time = 0;
        for (Episode episode : podcast.getEpisodes()) {
            time += episode.getDuration();
            if (episode.equals(currentEpisode)) {
                time = 0;
            }
        }
        newRemainedTime -= time;

        return newRemainedTime;
    }
}
