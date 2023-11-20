package main;

import java.util.ArrayList;

public class Podcast {
    public String name;
    public String owner;
    public ArrayList<Episode> episodes;
    public int timeWatched;

    public Podcast(fileio.input.PodcastInput podcastInput) {
        this.name = podcastInput.getName();
        this.owner = podcastInput.getOwner();
        this.episodes = new ArrayList<>();
        for (fileio.input.EpisodeInput episodeInput : podcastInput.getEpisodes()) {
            this.episodes.add(new Episode(episodeInput));
        }
        this.timeWatched = 0;
    }

    public int totalDuration() {
        int totalDuration = 0;
        for (Episode episode : episodes) {
            totalDuration += episode.duration;
        }
        return totalDuration;
    }

    public Episode getCurrentEpisode(int remainedTime) {
        int episodeTime = this.totalDuration() - remainedTime;
        int time = 0;

        for (Episode episode : episodes) {
            time += episode.duration;
            if (time >= episodeTime) {
                return episode;
            }
        }

        return null;
    }
}
