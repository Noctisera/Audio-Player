package main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Playlist {
    @Getter
    public String name;
    @Getter
    public String owner;
    @Getter
    public ArrayList<Song> songs;
    @Getter
    public String visibility;
    @Getter
    @Setter
    public int followers;
    public Song playingSong = null;
    public Song loopedSong = null;

    public Playlist(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.songs = new ArrayList<>();
        this.visibility = "public";
        this.followers = 0;
    }

    public Playlist(Playlist playlist) {
        this.name = playlist.name;
        this.owner = playlist.owner;
        this.songs = new ArrayList<>(playlist.songs);
        this.visibility = playlist.visibility;
        this.followers = playlist.followers;
        this.playingSong = playlist.playingSong;
        this.loopedSong = playlist.loopedSong;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public ArrayList<String> getSongNames() {
        ArrayList<String> songNames = new ArrayList<>();
        for (Song song : songs) {
            songNames.add(song.name);
        }
        return songNames;
    }

    public int getPlayingSongId() {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(playingSong)) {
                return i;
            }
        }
        return -1;
    }

    public int getNumberOfFollowers() {
        return followers;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int totalDuration() {
        int totalDuration = 0;
        for (Song song : songs) {
            totalDuration += song.duration;
        }
        return totalDuration;
    }
}
