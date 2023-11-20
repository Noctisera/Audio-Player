package Commands;

import Commands.States.LoadState;
import main.Episode;
import main.Playlist;
import main.Podcast;
import main.Song;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Status {
    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, LoadState loadState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        LinkedHashMap<String, Object> stats = new LinkedHashMap<>();

        String user = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        String name = null;
        int remainedTime = 0;
        String repeat = loadState.repeatState;
        boolean shuffle = loadState.shuffleState;
        boolean paused = loadState.playbackState.equals("pause");

        if (loadState.loadedType == null || loadState.remainingTime <= 0) {
            name = "";
            paused = true;
        } else {
            remainedTime = loadState.remainingTime;
            switch (loadState.loadedType) {
                case "song" -> name = loadState.loadedSong.name;
                case "playlist" -> {
                    if (loadState.repeatState.equals("Repeat Current Song")) {
                        name = loadState.loadedPlaylist.loopedSong.name;
                    } else {
                        name = loadState.loadedPlaylist.playingSong.name;
                    }
                }
                case "podcast" -> {
                    name = loadState.loadedPodcast.getCurrentEpisode(loadState.remainingTime).name;
                    remainedTime = substituteFromPodcast(remainedTime, loadState.loadedPodcast,
                            loadState.loadedPodcast.getCurrentEpisode(loadState.remainingTime));
                }
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

    public int substituteFromPodcast(int remainedTime, Podcast podcast, Episode currentEpisode) {
        int time = 0;
        for (Episode episode : podcast.episodes) {
            time += episode.duration;
            if (episode.equals(currentEpisode)) {
                time = 0;
            }
        }
        remainedTime -= time;

        return remainedTime;
    }

    public int substituteFromPlaylist(int remainedTime, Playlist playlist, Song currentSong) {
        int time = 0;
        for (Song song : playlist.songs) {
            time += song.duration;
            if (song.equals(currentSong)) {
                time = 0;
            }
        }
        remainedTime -= time;

        return remainedTime;
    }
}
