package commands.States;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import main.Playlist;
import main.Podcast;
import main.Song;

/**
 * The load state of music player
 *
 * <p>Stores the data of the loaded track and it's options like Play/Pause, Repeat, Shuffle
 * state. There is also time data stored that necessary for calculating the remaining
 * time in later commands.</p>
 */
@Getter @Accessors(chain = true) @Setter
public final class LoadState {
    /* Load information */
    private Song loadedSong = null;
    private Podcast loadedPodcast = null;
    private Playlist loadedPlaylist = null;
    private String loadedType = null;
    private String playbackState = "play";
    private String repeatState = "No Repeat";
    private boolean shuffleState = false;
    private int totalDuration = 0;
    private int remainingTime = 0;
    private int lastTimestamp = 0;
    /* Used for shuffle */
    private Playlist oldPlaylist = null;

    public LoadState() {
    }

    public LoadState(final LoadState loadState) {
        this.loadedSong = loadState.loadedSong;
        this.loadedPodcast = loadState.loadedPodcast;
        this.loadedPlaylist = loadState.loadedPlaylist;
        this.loadedType = loadState.loadedType;
        this.playbackState = loadState.playbackState;
        this.repeatState = loadState.repeatState;
        this.shuffleState = loadState.shuffleState;
        this.lastTimestamp = loadState.lastTimestamp;
        this.totalDuration = loadState.totalDuration;
        this.remainingTime = loadState.remainingTime;
        this.oldPlaylist = loadState.oldPlaylist;
    }

    /**
     * @return the loaded song
     */
    public boolean getShuffleState() {
        return shuffleState;
    }

    /**
     * Clears the load state.
     */
    public void clear() {
        if (loadedPodcast != null && loadedType != null && loadedType.equals("podcast")) {
            loadedPodcast.setTimeWatched(loadedPodcast.totalDuration() - this.remainingTime);
        }

        loadedSong = null;
        loadedPodcast = null;
        loadedPlaylist = null;
        loadedType = null;
        playbackState = "pause";
        repeatState = "No Repeat";
        shuffleState = false;
    }
}
