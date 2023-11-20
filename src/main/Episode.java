package main;

import lombok.Getter;

public class Episode {
    @Getter
    public String name;
    @Getter
    public int duration;
    public String description;

    public Episode(fileio.input.EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        this.description = episodeInput.getDescription();
    }
}
