package main;

import lombok.Getter;

@Getter
public final class Episode {
    private final String name;
    private final int duration;

    public Episode(final fileio.input.EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        String description = episodeInput.getDescription();
    }
}
