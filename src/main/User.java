package main;

import commands.States.LoadState;
import commands.States.SearchState;
import commands.States.SelectState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter @Setter @Accessors(chain = true)
public final class User {
    private final String username;
    private final int age;
    private final String city;
    private final ArrayList<Playlist> playlists;
    private final ArrayList<Playlist> followedPlaylists;
    private final ArrayList<Song> likedSongs;
    private SearchState searchState = new SearchState();
    private SelectState selectState = new SelectState();
    private LoadState loadState = new LoadState();

    public User(final fileio.input.UserInput userInput) {
        this.username = userInput.getUsername();
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.playlists = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
    }
}
