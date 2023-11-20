package Commands;

import Commands.States.LoadState;
import Commands.States.SearchState;
import Commands.States.SelectState;
import SearchBar.Search;
import SearchBar.Select;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Command {
    SearchState searchState = new SearchState();
    SelectState selectState = new SelectState();
    LoadState loadState = new LoadState();

    public void executeCommands(Library library, ArrayList<HashMap<String, Object>> commands,
                                ArrayNode outputs) {
        for (HashMap<String, Object> command : commands) {
            if (command.get("command").equals("search")) {
                executeSearch(library, command, outputs);
            } else if (command.get("command").equals("select")) {
                executeSelect(command, outputs);
            } else if (command.get("command").equals("load")) {
                executeLoad(command, outputs);
            } else if (command.get("command").equals("playPause")) {
                executePlayPause(command, outputs);
            } else if (command.get("command").equals("repeat")) {
                executeRepeat(command, outputs);
            } else if (command.get("command").equals("shuffle")) {
                executeShuffle(command, outputs);
            } else if (command.get("command").equals("forward")) {
                executeForward(command, outputs);
            } else if (command.get("command").equals("backward")) {
                executeBackward(command, outputs);
            } else if (command.get("command").equals("like")) {
                executeLike(library, command, outputs);
            } else if (command.get("command").equals("next")) {
                executeNext(command, outputs);
            } else if (command.get("command").equals("prev")) {
                executePrev(command, outputs);
            } else if (command.get("command").equals("addRemoveInPlaylist")) {
                executeAddRemoveInPlaylist(library, command, outputs);
            } else if (command.get("command").equals("status")) {
                executeStatus(command, outputs);
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


    public void executeSearch(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));
        Search search = new Search();
        searchState = search.returnSearch(library, command);
        LinkedHashMap<String, Object> output = search.returnOutput(command, searchState);

        selectState.Clear();
        loadState.Clear();

        outputs.addPOJO(output);
    }

    public void executeSelect(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));
        Select select = new Select();
        selectState = select.returnSelect(command, searchState);
        LinkedHashMap<String, Object> output = select.returnOutput(command, selectState, searchState);

        searchState.Clear();

        outputs.addPOJO(output);
    }

    public void executeLoad(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));
        Load load = new Load();
        loadState = load.returnLoad(selectState, loadState);
        LinkedHashMap<String, Object> output = load.returnOutput(command, loadState, selectState);

        selectState.Clear();

        outputs.addPOJO(output);
    }

    public void executePlayPause(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        PlayPause playPause = new PlayPause();
        playPause.execute(loadState);
        LinkedHashMap<String, Object> output = playPause.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeRepeat(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Repeat repeat = new Repeat();
        LinkedHashMap<String, Object> output = repeat.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeShuffle(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Shuffle shuffle = new Shuffle();
        LinkedHashMap<String, Object> output = shuffle.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeForward(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Forward forward = new Forward();
        LinkedHashMap<String, Object> output = forward.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeBackward(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Backward backward = new Backward();
        LinkedHashMap<String, Object> output = backward.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeLike(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Like like = new Like();
        LinkedHashMap<String, Object> output = like.returnOutput(library, command, loadState);

        outputs.addPOJO(output);
    }

    public void executeNext(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Next next = new Next();
        LinkedHashMap<String, Object> output = next.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executePrev(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Prev prev = new Prev();
        LinkedHashMap<String, Object> output = prev.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeAddRemoveInPlaylist(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        AddRemoveInPlaylist addRemoveInPlaylist = new AddRemoveInPlaylist();
        LinkedHashMap<String, Object> output = addRemoveInPlaylist.returnOutput(library, command, loadState);

        outputs.addPOJO(output);
    }

    public void executeStatus(HashMap<String, Object> command, ArrayNode outputs) {
        Helper.UpdateState(loadState, (int) command.get("timestamp"));

        Status status = new Status();
        LinkedHashMap<String, Object> output = status.returnOutput(command, loadState);

        outputs.addPOJO(output);
    }

    public void executeCreatePlaylist(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        CreatePlaylist createPlaylist = new CreatePlaylist();
        LinkedHashMap<String, Object> output = createPlaylist.returnOutput(library, command, loadState);

        outputs.addPOJO(output);
    }

    public void executeSwitchVisibility(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        SwitchVisibility switchVisibility = new SwitchVisibility();
        LinkedHashMap<String, Object> output = switchVisibility.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    public void executeFollowPlaylist(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        FollowPlaylist followPlaylist = new FollowPlaylist();
        LinkedHashMap<String, Object> output = followPlaylist.returnOutput(library, command, selectState);

        outputs.addPOJO(output);
    }

    public void executeShowPlaylists(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        ShowPlaylists showPlaylists = new ShowPlaylists();
        LinkedHashMap<String, Object> output = showPlaylists.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    public void executeShowPreferredSongs(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        ShowPrefferedSongs showPrefferedSongs = new ShowPrefferedSongs();
        LinkedHashMap<String, Object> output = showPrefferedSongs.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    public void executeGetTop5Songs(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        GetTop5Songs getTop5Songs = new GetTop5Songs();
        LinkedHashMap<String, Object> output = getTop5Songs.returnOutput(library, command);

        outputs.addPOJO(output);
    }

    public void executeGetTop5Playlists(Library library, HashMap<String, Object> command, ArrayNode outputs) {
        GetTop5Playlists getTop5Playlists = new GetTop5Playlists();
        LinkedHashMap<String, Object> output = getTop5Playlists.returnOutput(library, command);

        outputs.addPOJO(output);
    }
}
