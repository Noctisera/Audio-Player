package Commands;

import Commands.States.LoadState;
import main.Playlist;

public class Helper {
    static public void UpdateState(LoadState loadState, int timestamp) {
        // Update the remaining time and repeat state
        if (loadState.loadedType == null || loadState.playbackState.equals("pause")) {
            loadState.lastTimestamp = timestamp;
            return;
        }

        loadState.remainingTime = loadState.remainingTime - (timestamp - loadState.lastTimestamp);
        loadState.lastTimestamp = timestamp;

        if (loadState.loadedType.equals("song") || loadState.loadedType.equals("podcast")) {
            switch (loadState.repeatState) {
                case "No Repeat" -> {
                    if (loadState.remainingTime < 0) {
                        loadState.remainingTime = 0;
                        loadState.playbackState = "pause";
                        loadState.shuffleState = false;
                    }
                }
                case "Repeat Once" -> {
                    if (loadState.remainingTime <= 0) {
                        loadState.remainingTime += loadState.totalDuration;
                        loadState.repeatState = "No Repeat";
                    }

                    if (loadState.remainingTime <= 0) {
                        loadState.remainingTime = 0;
                        loadState.playbackState = "pause";
                        loadState.shuffleState = false;
                    }
                }
                case "Repeat Infinite" -> {
                    while (loadState.remainingTime <= 0) {
                        loadState.remainingTime += loadState.totalDuration;
                    }
                }
            }
        } else {
            Playlist playlist = loadState.loadedPlaylist;
            switch (loadState.repeatState) {
                case "No Repeat" -> {
                    if (loadState.remainingTime <= 0) {
                        // Add the duration of the following songs to the remaining time
                        for (int i = playlist.getPlayingSongId() + 1; i < playlist.songs.size() &&
                                loadState.remainingTime <= 0; i++) {
                            loadState.remainingTime += playlist.songs.get(i).duration;
                            if (playlist.songs.get(i).equals(playlist.playingSong)) {
                                break;
                            }
                            playlist.playingSong = playlist.songs.get(i);
                        }
                    }

                    if (loadState.remainingTime <= 0) {
                        loadState.remainingTime = 0;
                        loadState.playbackState = "pause";
                        loadState.shuffleState = false;
                    }
                }
                case "Repeat All" -> {
                    while (loadState.remainingTime <= 0) {
                        // Add the duration of the following songs to the remaining time
                        for (int i = playlist.getPlayingSongId() + 1; i < playlist.songs.size() &&
                                loadState.remainingTime <= 0; i++) {
                            loadState.remainingTime += playlist.songs.get(i).duration;
                            if (playlist.songs.get(i).equals(playlist.playingSong)) {
                                break;
                            }
                            playlist.playingSong = playlist.songs.get(i);
                        }

                        if (loadState.remainingTime <= 0) {
                            loadState.remainingTime += playlist.songs.get(0).duration;
                            playlist.playingSong = playlist.songs.get(0);
                        }
                    }
                }
                case "Repeat Current Song" -> {
                    while (loadState.remainingTime <= 0) {
                        loadState.remainingTime += playlist.loopedSong.duration;
                    }
                }
            }
        }
    }
}
