package Commands.States;

import main.Playlist;
import main.Podcast;
import main.Song;

public class LoadState {
    /* Load information */
    public Song loadedSong = null;
    public Podcast loadedPodcast = null;
    public Playlist loadedPlaylist = null;
    public String loadedType = null;
    /* Playback information */
    public String playbackState = "play";
    public String repeatState = "No Repeat";
    public boolean shuffleState = false;
    public int totalDuration = 0;
    public int remainingTime = 0;
    public int lastTimestamp = 0;
    // Only used for shuffle
    public Playlist oldPlaylist = null;

    public LoadState() {
    }

    public LoadState(LoadState loadState) {
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

    public void Clear() {
        if (loadedPodcast != null && loadedType != null && loadedType.equals("podcast")) {
            loadedPodcast.timeWatched = loadedPodcast.totalDuration() - this.remainingTime;
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
