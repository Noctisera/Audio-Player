package Commands.States;

import main.Playlist;
import main.Podcast;
import main.Song;

public class SelectState {
    public Song selectedSong = null;
    public Podcast selectedPodcast = null;
    public Playlist selectedPlaylist = null;
    public String selectedType = null;

    public void Clear() {
        selectedSong = null;
        selectedPodcast = null;
        selectedPlaylist = null;
        selectedType = null;
    }
}
