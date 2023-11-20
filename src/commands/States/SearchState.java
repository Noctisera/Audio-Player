package commands.States;

import lombok.Getter;
import lombok.Setter;
import main.Playlist;
import main.Podcast;
import main.Song;

import java.util.ArrayList;

@Getter
public class SearchState {
    @Setter
    private ArrayList<Song> searchSongs = null;
    @Setter
    private ArrayList<Playlist> searchPlaylists = null;
    @Setter
    private ArrayList<Podcast> searchPodcasts = null;
    @Setter
    private String searchedType = null;

    /**
     * clears the state
     */
    public void clear() {
        searchSongs = null;
        searchPlaylists = null;
        searchPodcasts = null;
        searchedType = null;
    }
}
