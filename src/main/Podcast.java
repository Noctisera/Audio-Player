package main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public final class Podcast {
    private final String name;
    private final String owner;
    private final ArrayList<Episode> episodes;
    private int timeWatched;

    /**
     * @param podcastInput the input of the podcast
     */
    public Podcast(final fileio.input.PodcastInput podcastInput) {
        this.name = podcastInput.getName();
        this.owner = podcastInput.getOwner();
        this.episodes = new ArrayList<>();
        for (fileio.input.EpisodeInput episodeInput : podcastInput.getEpisodes()) {
            this.episodes.add(new Episode(episodeInput));
        }
        this.timeWatched = 0;
    }

    /**
     * @return the total duration of the podcast
     */
    public int totalDuration() {
        int totalDuration = 0;
        for (Episode episode : episodes) {
            totalDuration += episode.getDuration();
        }
        return totalDuration;
    }

    /**
     * @param remainedTime the remaining time of the podcast
     * @return the current episode of the podcast
     */
    public Episode getCurrentEpisode(final int remainedTime) {
        int episodeTime = this.totalDuration() - remainedTime;
        int time = 0;

        for (Episode episode : episodes) {
            time += episode.getDuration();
            if (time >= episodeTime) {
                return episode;
            }
        }

        return null;
    }
}
