package main;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class User {
    private final String username;
    private final int age;
    private final String city;
    private final ArrayList<Playlist> playlists;
    private final ArrayList<Playlist> followedPlaylists;
    private final ArrayList<Song> likedSongs;

    public User(final fileio.input.UserInput userInput) {
        this.username = userInput.getUsername();
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.playlists = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
    }
}
