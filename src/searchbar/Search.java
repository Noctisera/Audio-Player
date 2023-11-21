package searchbar;

import commands.States.SearchState;
import main.Library;
import main.Podcast;
import main.Playlist;
import main.Song;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Search {
    /**
     * Updates the search state with the results of the search.
     *
     * @param library the library to search in
     * @param command the command to be executed
     * @return the search state
     */
    public SearchState returnSearch(final Library library, final HashMap<String, Object> command) {
        String type = (String) command.get("type");
        HashMap<String, Object> searchParams = (HashMap<String, Object>) command.get("filters");
        SearchState searchState = new SearchState();
        SearchBar searchBar = new SearchBar();

        switch (type) {
            case "song" -> {
                searchState.setSearchedType("song");
                searchState.setSearchSongs(searchBar.searchSong(searchParams, library));
            }
            case "playlist" -> {
                searchState.setSearchedType("playlist");
                User user = library.getUser((String) command.get("username"));
                searchState.setSearchPlaylists(searchBar.searchPlaylist(searchParams,
                        library, user));
            }
            case "podcast" -> {
                searchState.setSearchedType("podcast");
                searchState.setSearchPodcasts(searchBar.searchPodcast(searchParams, library));
            }
            default -> {
                searchState.setSearchedType(null);
            }
        }

        return searchState;
    }

    /**
     * Returns the output of the search command.
     *
     * @param command    the command to be executed
     * @param searchState the search state
     * @return the output
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final SearchState searchState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        ArrayList<String> results = new ArrayList<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");

        switch (searchState.getSearchedType()) {
            case "song" -> {
                for (Song song : searchState.getSearchSongs()) {
                    results.add(song.getName());
                }
                message = "Search returned " + searchState.getSearchSongs().size() + " results";
            }
            case "playlist" -> {
                for (Playlist playlist : searchState.getSearchPlaylists()) {
                    results.add(playlist.getName());
                }
                message = "Search returned " + searchState.getSearchPlaylists().size() + " results";
            }
            case "podcast" -> {
                for (Podcast podcast : searchState.getSearchPodcasts()) {
                    results.add(podcast.getName());
                }
                message = "Search returned " + searchState.getSearchPodcasts().size() + " results";
            }
            default -> {
                message = "Please specify the type of media you would like to search for.";
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
