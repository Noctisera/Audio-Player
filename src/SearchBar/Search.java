package SearchBar;

import Commands.States.SearchState;
import main.Library;
import main.Playlist;
import main.Podcast;
import main.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Search {
    public SearchState returnSearch(Library library, HashMap<String, Object> command) {
        String type = (String) command.get("type");
        HashMap<String, Object> searchParams = (HashMap<String, Object>) command.get("filters");
        SearchState searchState = new SearchState();
        SearchBar searchBar = new SearchBar();

        switch (type) {
            case "song" -> {
                searchState.searchedType = "song";
                searchState.searchSongs = searchBar.SearchSong(searchParams, library);
            }
            case "playlist" -> {
                searchState.searchedType = "playlist";
                searchState.searchPlaylists = searchBar.SearchPlaylist(searchParams, library);
            }
            case "podcast" -> {
                searchState.searchedType = "podcast";
                searchState.searchPodcasts = searchBar.SearchPodcast(searchParams, library);
            }
        }

        return searchState;
    }

    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, SearchState searchState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        ArrayList<String> results = new ArrayList<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        switch (searchState.searchedType) {
            case "song" -> {
                for (Song song : searchState.searchSongs) {
                    results.add(song.name);
                }
                message = "Search returned " + searchState.searchSongs.size() + " results";
            }
            case "playlist" -> {
                for (Playlist playlist : searchState.searchPlaylists) {
                    results.add(playlist.name);
                }
                message = "Search returned " + searchState.searchPlaylists.size() + " results";
            }
            case "podcast" -> {
                for (Podcast podcast : searchState.searchPodcasts) {
                    results.add(podcast.name);
                }
                message = "Search returned " + searchState.searchPodcasts.size() + " results";
            }
        }

        output.put("command", "search");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);
        output.put("results", results);

        return output;
    }
}
