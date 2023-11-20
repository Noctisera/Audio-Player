package searchbar;

import commands.States.SearchState;
import commands.States.SelectState;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Select {
    /**
     * @param command     the command to be executed
     * @param searchState the state of the search command
     * @return the state of the select command
     */
    public SelectState returnSelect(final HashMap<String, Object> command,
                                    final SearchState searchState) {
        SearchBar searchBar = new SearchBar();
        int itemNumber = (int) command.get("itemNumber");
        SelectState selectState = new SelectState();

        if (searchState.getSearchedType() == null) {
            return selectState;
        }

        switch (searchState.getSearchedType()) {
            case "song" -> {
                selectState.setSelectedSong(searchBar.selectSong(searchState.
                        getSearchSongs(), itemNumber));
                selectState.setSelectedType("song");
            }
            case "playlist" -> {
                selectState.setSelectedPlaylist(searchBar.selectPlaylist(searchState.
                        getSearchPlaylists(), itemNumber));
                selectState.setSelectedType("playlist");
            }
            case "podcast" -> {
                selectState.setSelectedPodcast(searchBar.selectPodcast(searchState.
                        getSearchPodcasts(), itemNumber));
                selectState.setSelectedType("podcast");
            }
            default -> System.out.println("Invalid search type.");
        }

        return selectState;
    }

    /**
     * @param command     the command to be executed
     * @param selectState the state of the select command
     * @param searchState the state of the search command
     * @return the output of the select command
     */
    public LinkedHashMap<String, Object> returnOutput(final HashMap<String, Object> command,
                                                      final SelectState selectState,
                                                      final SearchState searchState) {
        LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        String message = null;

        String username = (String) command.get("username");
        int timestamp = (int) command.get("timestamp");
        int itemNumber = (int) command.get("itemNumber");

        if (selectState.getSelectedType() == null) {
            message = "Please conduct a search before making a selection.";
        } else {
            switch (selectState.getSelectedType()) {
                case "song" -> {
                    if (itemNumber > searchState.getSearchSongs().size()) {
                        message = "The selected ID is too high.";
                        selectState.clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.getSelectedSong().getName()
                            + ".";
                }
                case "playlist" -> {
                    if (itemNumber > searchState.getSearchPlaylists().size()) {
                        message = "The selected ID is too high.";
                        selectState.clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.getSelectedPlaylist().
                            getName() + ".";
                }
                case "podcast" -> {
                    if (itemNumber > searchState.getSearchPodcasts().size()) {
                        message = "The selected ID is too high.";
                        selectState.clear();
                        break;
                    }

                    message = "Successfully selected " + selectState.getSelectedPodcast().getName()
                            + ".";
                }
                default -> System.out.println("Invalid search type.");
            }
        }

        output.put("command", "select");
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);

        return output;
    }
}
