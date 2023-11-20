package Commands.States;

import main.Playlist;
import main.Podcast;
import main.Song;

import java.util.ArrayList;

public class SearchState {
    public ArrayList<Song> searchSongs = null;
    public ArrayList<Playlist> searchPlaylists = null;
    public ArrayList<Podcast> searchPodcasts = null;
    public String searchedType = null;

    public void Clear() {
        searchSongs = null;
        searchPlaylists = null;
        searchPodcasts = null;
        searchedType = null;
    }
}
