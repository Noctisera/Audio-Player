package main;

import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;

public class Song {
    @Getter
    public String name;
    @Getter
    public int duration;
    public String album;
    public ArrayList<String> tags;
    public String lyrics;
    public String genre;
    public Integer releaseYear;
    public String artist;

    public int likes;

    public Song(SongInput songInput) {
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

    public int getNumberOfLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
