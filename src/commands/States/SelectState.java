package commands.States;

import lombok.Getter;
import lombok.Setter;
import main.Playlist;
import main.Podcast;
import main.Song;

@Getter
public class SelectState {
    @Setter
    private Song selectedSong = null;
    @Setter
    private Podcast selectedPodcast = null;
    @Setter
    private Playlist selectedPlaylist = null;
    @Setter
    private String selectedType = null;

    /**
     * clears the state
     */
    public void clear() {
        selectedSong = null;
        selectedPodcast = null;
        selectedPlaylist = null;
        selectedType = null;
    }
}
