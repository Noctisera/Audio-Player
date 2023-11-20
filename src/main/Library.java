package main;

import fileio.input.LibraryInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Library {
    private final ArrayList<Song> songs;
    private final ArrayList<User> users;
    private final ArrayList<Podcast> podcasts;
    private final ArrayList<Playlist> playlists;

    /**
     * @param songs     the songs in the library
     * @param users     the users in the library
     * @param podcasts  the podcasts in the library
     */
    public Library(final ArrayList<Song> songs, final ArrayList<User> users,
                   final ArrayList<Podcast> podcasts) {
        this.songs = songs;
        this.users = users;
        this.podcasts = podcasts;
        this.playlists = new ArrayList<>();
    }

    /**
     * @param libraryInput the input library
     */
    public Library(final LibraryInput libraryInput) {
        this.songs = new ArrayList<>();
        this.users = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        this.playlists = new ArrayList<>();

        for (fileio.input.SongInput songInput : libraryInput.getSongs()) {
            this.songs.add(new Song(songInput));
        }

        for (fileio.input.UserInput userInput : libraryInput.getUsers()) {
            this.users.add(new User(userInput));
        }

        for (fileio.input.PodcastInput podcastInput : libraryInput.getPodcasts()) {
            this.podcasts.add(new Podcast(podcastInput));
        }
    }

    /**
     * @param username the username of the user
     * @return the user with the given username
     */
    public User getUser(final String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }
}
