package main;

import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public final class Song {
    private final String name;
    private final int duration;
    private final String album;
    private final ArrayList<String> tags;
    private final String lyrics;
    private final String genre;
    private final Integer releaseYear;
    private final String artist;

    public int likes;

    public Song(final SongInput songInput) {
        this.name = songInput.getName();
        this.duration = songInput.getDuration();
        this.album = songInput.getAlbum();
        this.tags = songInput.getTags();
        this.lyrics = songInput.getLyrics();
        this.genre = songInput.getGenre();
        this.releaseYear = songInput.getReleaseYear();
        this.artist = songInput.getArtist();
        this.likes = 0;
    }

    /**
     * @return the name of the song
     */
    public int getNumberOfLikes() {
        return likes;
    }

    /**
     * @param likes the number of likes of the song
     */
    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
