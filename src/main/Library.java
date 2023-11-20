package main;

import fileio.input.LibraryInput;
import lombok.Getter;

import java.util.ArrayList;

public class Library {
    @Getter
    public ArrayList<Song> songs;
    @Getter
    public ArrayList<User> users;
    @Getter
    public ArrayList<Podcast> podcasts;
    @Getter
    public ArrayList<Playlist> playlists;

    public Library(ArrayList<Song> songs, ArrayList<User> users, ArrayList<Podcast> podcasts) {
        this.songs = songs;
        this.users = users;
        this.podcasts = podcasts;
        this.playlists = new ArrayList<>();
    }

    public Library(LibraryInput libraryInput) {
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

    public User getUser(String username) {
        for (User user : this.users) {
            if (user.username.equals(username)) {
                return user;
            }
        }

        return null;
    }

    /*ArrayList<Playlist> GetTop5Playlists() {
        // Get the 5 most followed playlists
        ArrayList<Playlist> top5Playlists = new ArrayList<>();
        for (Playlist playlist : this.playlists) {
            if (top5Playlists.size() < 5) {
                top5Playlists.add(playlist);

                //Sort playlists by followers
                top5Playlists.sort(Comparator.comparingInt(p -> p.followers));
            } else {
                for (Playlist topPlaylist : top5Playlists) {
                    if (playlist.followers > topPlaylist.followers) {
                        top5Playlists.remove(topPlaylist);
                        top5Playlists.add(playlist);

                        //Sort playlists by followers
                        top5Playlists.sort(Comparator.comparingInt(p -> p.followers));
                        break;
                    }
                }
            }
        }

        return top5Playlists;
    }*/
}
