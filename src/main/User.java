package main;

import lombok.Getter;

import java.util.ArrayList;

public class User {
    @Getter
    public String username;
    int age;
    public String city;
    @Getter
    public ArrayList<Playlist> playlists;
    @Getter
    public ArrayList<Playlist> followedPlaylists;
    @Getter
    public ArrayList<Song> likedSongs;

    public User(fileio.input.UserInput userInput) {
        this.username = userInput.getUsername();
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.playlists = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
    }
}
