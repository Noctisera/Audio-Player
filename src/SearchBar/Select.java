package SearchBar;

import Commands.States.SearchState;
import Commands.States.SelectState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Select {
    public SelectState returnSelect(HashMap<String, Object> command, SearchState searchState) {
        SearchBar searchBar = new SearchBar();
        int itemNumber = (int) command.get("itemNumber");
        SelectState selectState = new SelectState();

        if (searchState.searchedType == null) {
            return selectState;
        }

        switch (searchState.searchedType) {
            case "song" -> {
                selectState.selectedSong = searchBar.SelectSong(searchState.searchSongs, itemNumber);
                selectState.selectedType = "song";
            }
            case "playlist" -> {
                selectState.selectedPlaylist = searchBar.SelectPlaylist(searchState.searchPlaylists, itemNumber);
                selectState.selectedType = "playlist";
            }
            case "podcast" -> {
                selectState.selectedPodcast = searchBar.SelectPodcast(searchState.searchPodcasts, itemNumber);
                selectState.selectedType = "podcast";
            }
        }

        return selectState;
    }

    public LinkedHashMap<String, Object> returnOutput(HashMap<String, Object> command, SelectState selectState,
                                                      SearchState searchState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int itemNumber = (int) command.get("itemNumber");

        if (selectState.selectedType == null) {
            message = "Please conduct a search before making a selection.";
        } else {
            switch (selectState.selectedType) {
                case "song" -> {
                    if (itemNumber > searchState.searchSongs.size()) {
                        message = "The selected ID is too high.";
                        selectState.Clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.selectedSong.name + ".";
                }
                case "playlist" -> {
                    if (itemNumber > searchState.searchPlaylists.size()) {
                        message = "The selected ID is too high.";
                        selectState.Clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.selectedPlaylist.name + ".";
                }
                case "podcast" -> {
                    if (itemNumber > searchState.searchPodcasts.size()) {
                        message = "The selected ID is too high.";
                        selectState.Clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.selectedPodcast.name + ".";
                }
            }
        }

        output.put("command", "select");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
