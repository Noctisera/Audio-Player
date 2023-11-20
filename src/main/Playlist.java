package main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public final class Playlist {
    private final String name;
    private final String owner;
    private ArrayList<Song> songs;
    private String visibility;
    private int followers;
    private Song playingSong = null;
    private Song loopedSong = null;

    public Playlist(final String name, final String owner) {
        this.name = name;
        this.owner = owner;
        this.songs = new ArrayList<>();
        this.visibility = "public";
        this.followers = 0;
    }

    public Playlist(final Playlist playlist) {
        this.name = playlist.name;
        this.owner = playlist.owner;
        this.songs = new ArrayList<>(playlist.songs);
        this.visibility = playlist.visibility;
        this.followers = playlist.followers;
        this.playingSong = playlist.playingSong;
        this.loopedSong = playlist.loopedSong;
    }

    /**
     * @param song the song to be added
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * @param song the song to be removed
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * @return the names of the songs in the playlist in an ArrayList
     */
    public ArrayList<String> getSongNames() {
        ArrayList<String> songNames = new ArrayList<>();
        for (Song song : songs) {
            songNames.add(song.getName());
        }
        return songNames;
    }

    /**
     * @return the artists of the songs in the playlist in an ArrayList
     */
    public int getPlayingSongId() {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(playingSong)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return the artists of the songs in the playlist in an ArrayList
     */
    public int getNumberOfFollowers() {
        return followers;
    }

    /**
     * @return the artists of the songs in the playlist in an ArrayList
     */
    public int totalDuration() {
        int totalDuration = 0;
        for (Song song : songs) {
            totalDuration += song.getDuration();
        }
        return totalDuration;
    }
}
