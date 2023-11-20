package commands.States;

import lombok.Getter;
import lombok.Setter;
import main.Playlist;
import main.Podcast;
import main.Song;

@Getter
public final class LoadState {
    /* Load information */
    @Setter
    private Song loadedSong = null;
    @Setter
    private Podcast loadedPodcast = null;
    @Setter
    private Playlist loadedPlaylist = null;
    @Setter
    private String loadedType = null;
    /* Playback information */
    @Getter @Setter
    private String playbackState = "play";
    @Getter @Setter
    private String repeatState = "No Repeat";
    @Getter @Setter
    private boolean shuffleState = false;
    @Getter @Setter
    private int totalDuration = 0;
    @Getter @Setter
    private int remainingTime = 0;
    @Getter @Setter
    private int lastTimestamp = 0;
    // Only used for shuffle
    @Getter @Setter
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
