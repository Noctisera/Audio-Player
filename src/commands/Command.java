package commands;

import commands.States.LoadState;
import commands.States.SearchState;
import commands.States.SelectState;
import main.User;
import searchbar.Search;
import searchbar.Select;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Command {
    /**
     * @param library  the library
     * @param commands the list of commands to be executed
     * @param outputs  the list of outputs
     */
    public void executeCommands(final Library library, final ArrayList<HashMap<String, Object>>
            commands, final ArrayNode outputs) {
        for (HashMap<String, Object> command : commands) {
            if (command.get("command").equals("search")) {
                executeSearch(library, command, outputs);
            } else if (command.get("command").equals("select")) {
                executeSelect(library, command, outputs);
            } else if (command.get("command").equals("load")) {
                executeLoad(library, command, outputs);
            } else if (command.get("command").equals("playPause")) {
                executePlayPause(library, command, outputs);
            } else if (command.get("command").equals("repeat")) {
                executeRepeat(library, command, outputs);
            } else if (command.get("command").equals("shuffle")) {
                executeShuffle(library, command, outputs);
            } else if (command.get("command").equals("forward")) {
                executeForward(library, command, outputs);
            } else if (command.get("command").equals("backward")) {
                executeBackward(library, command, outputs);
            } else if (command.get("command").equals("like")) {
                executeLike(library, command, outputs);
            } else if (command.get("command").equals("next")) {
                executeNext(library, command, outputs);
            } else if (command.get("command").equals("prev")) {
                executePrev(library, command, outputs);
            } else if (command.get("command").equals("addRemoveInPlaylist")) {
                executeAddRemoveInPlaylist(library, command, outputs);
            } else if (command.get("command").equals("status")) {
                executeStatus(library, command, outputs);
            } else if (command.get("command").equals("createPlaylist")) {
                executeCreatePlaylist(library, command, outputs);
            } else if (command.get("command").equals("switchVisibility")) {
                executeSwitchVisibility(library, command, outputs);
            } else if (command.get("command").equals("follow")) {
                executeFollowPlaylist(library, command, outputs);
            } else if (command.get("command").equals("showPlaylists")) {
                executeShowPlaylists(library, command, outputs);
            } else if (command.get("command").equals("showPreferredSongs")) {
                executeShowPreferredSongs(library, command, outputs);
            } else if (command.get("command").equals("getTop5Songs")) {
                executeGetTop5Songs(library, command, outputs);
            } else if (command.get("command").equals("getTop5Playlists")) {
                executeGetTop5Playlists(library, command, outputs);
            }
        }
    }


    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeSearch(final Library library, final HashMap<String, Object> command,
                              final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));
        Search search = new Search();
        searchState = search.returnSearch(library, command);
        LinkedHashMap<String, Object> output = search.returnOutput(command, searchState);

        selectState.clear();
        loadState.clear();

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeSelect(final Library library, final HashMap<String, Object> command,
                              final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));
        Select select = new Select();
        selectState = select.returnSelect(command, searchState);
        LinkedHashMap<String, Object> output = select.returnOutput(command, selectState,
                searchState);

        searchState.clear();

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeLoad(final Library library, final HashMap<String, Object> command,
                            final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));
        Load load = new Load();
        loadState = load.returnLoad(selectState, loadState);
        LinkedHashMap<String, Object> output = load.returnOutput(command, loadState, selectState);

        selectState.clear();

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executePlayPause(final Library library, final HashMap<String, Object> command,
                                 final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        PlayPause playPause = new PlayPause();
        playPause.execute(loadState);
        LinkedHashMap<String, Object> output = playPause.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeRepeat(final Library library, final HashMap<String, Object> command,
                              final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Repeat repeat = new Repeat();
        LinkedHashMap<String, Object> output = repeat.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeShuffle(final Library library, final HashMap<String, Object> command,
                               final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Shuffle shuffle = new Shuffle();
        LinkedHashMap<String, Object> output = shuffle.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeForward(final Library library, final HashMap<String, Object> command,
                               final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Forward forward = new Forward();
        LinkedHashMap<String, Object> output = forward.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeBackward(final Library library, final HashMap<String, Object> command,
                                final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Backward backward = new Backward();
        LinkedHashMap<String, Object> output = backward.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeLike(final Library library, final HashMap<String, Object> command,
                            final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Like like = new Like();
        LinkedHashMap<String, Object> output = like.returnOutput(library, command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeNext(final Library library, final HashMap<String, Object> command,
                            final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Next next = new Next();
        LinkedHashMap<String, Object> output = next.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executePrev(final Library library, final HashMap<String, Object> command,
                            final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Prev prev = new Prev();
        LinkedHashMap<String, Object> output = prev.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeAddRemoveInPlaylist(final Library library, final HashMap<String, Object>
            command, final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SearchState searchState = user.getSearchState();
        SelectState selectState = user.getSelectState();
        LoadState loadState = user.getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        AddRemoveInPlaylist addRemoveInPlaylist = new AddRemoveInPlaylist();
        LinkedHashMap<String, Object> output = addRemoveInPlaylist.returnOutput(library, command,
                loadState);

        outputs.addPOJO(output);

        user.setSearchState(searchState);
        user.setSelectState(selectState);
        user.setLoadState(loadState);
    }

    /**
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeStatus(final Library library, final HashMap<String, Object> command,
                              final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        LoadState loadState = Objects.requireNonNull(user).getLoadState();

        Helper.updateState(loadState, (int) command.get("timestamp"));

        Status status = new Status();
        LinkedHashMap<String, Object> output = status.returnOutput(command, loadState);

        outputs.addPOJO(output);

        user.setLoadState(loadState);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeCreatePlaylist(final Library library, final HashMap<String, Object> command,
                                      final ArrayNode outputs) {
        CreatePlaylist createPlaylist = new CreatePlaylist();
        LinkedHashMap<String, Object> output = createPlaylist.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeSwitchVisibility(final Library library, final HashMap<String, Object>
            command, final ArrayNode outputs) {
        SwitchVisibility switchVisibility = new SwitchVisibility();
        LinkedHashMap<String, Object> output = switchVisibility.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeFollowPlaylist(final Library library, final HashMap<String, Object> command,
                                      final ArrayNode outputs) {
        String username = (String) command.get("username");
        User user = library.getUser(username);
        SelectState selectState = Objects.requireNonNull(user).getSelectState();

        FollowPlaylist followPlaylist = new FollowPlaylist();
        LinkedHashMap<String, Object> output = followPlaylist.returnOutput(library, command,
                selectState);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeShowPlaylists(final Library library, final HashMap<String, Object> command,
                                     final ArrayNode outputs) {
        ShowPlaylists showPlaylists = new ShowPlaylists();
        LinkedHashMap<String, Object> output = showPlaylists.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeShowPreferredSongs(final Library library, final HashMap<String, Object>
            command, final ArrayNode outputs) {
        ShowPrefferedSongs showPrefferedSongs = new ShowPrefferedSongs();
        LinkedHashMap<String, Object> output = showPrefferedSongs.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeGetTop5Songs(final Library library, final HashMap<String, Object> command,
                                    final ArrayNode outputs) {
        GetTop5Songs getTop5Songs = new GetTop5Songs();
        LinkedHashMap<String, Object> output = getTop5Songs.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    /**
     * @param library the library
     * @param command the command
     * @param outputs the list of outputs
     */
    public void executeGetTop5Playlists(final Library library, final HashMap<String, Object>
            command, final ArrayNode outputs) {
        GetTop5Playlists getTop5Playlists = new GetTop5Playlists();
        LinkedHashMap<String, Object> output = getTop5Playlists.returnOutput(library, command);

        outputs.addPOJO(output);
    }
}
