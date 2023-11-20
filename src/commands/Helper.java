package commands;

import commands.States.LoadState;
import main.Playlist;

final class Helper {
    /**
     * @param loadState the state of the load command
     * @param timestamp the timestamp of the current command
     */
    static void updateState(final LoadState loadState, final int timestamp) {
        // Update the remaining time and repeat state
        if (loadState.getLoadedType() == null || loadState.getPlaybackState().equals("pause")) {
            loadState.setLastTimestamp(timestamp);
            return;
        }

        loadState.setRemainingTime(loadState.getRemainingTime() - (timestamp
                - loadState.getLastTimestamp()));
        loadState.setLastTimestamp(timestamp);

        if (loadState.getLoadedType().equals("song") || loadState.getLoadedType().
                equals("podcast")) {
            switch (loadState.getRepeatState()) {
                case "No Repeat" -> {
                    if (loadState.getRemainingTime() < 0) {
                        loadState.setRemainingTime(0);
                        loadState.setPlaybackState("pause");
                        loadState.setShuffleState(false);
                    }
                }
                case "Repeat Once" -> {
                    if (loadState.getRemainingTime() <= 0) {
                        loadState.setRemainingTime(loadState.getRemainingTime()
                                + loadState.getTotalDuration());
                        loadState.setRepeatState("No Repeat");
                    }

                    if (loadState.getRemainingTime() <= 0) {
                        loadState.setRemainingTime(0);
                        loadState.setPlaybackState("pause");
                        loadState.setShuffleState(false);
                    }
                }
                case "Repeat Infinite" -> {
                    while (loadState.getRemainingTime() <= 0) {
                        loadState.setRemainingTime(loadState.getRemainingTime()
                                + loadState.getTotalDuration());
                    }
                }
                default -> System.out.println("Invalid repeat state.");
            }
        } else {
            Playlist playlist = loadState.getLoadedPlaylist();
            switch (loadState.getRepeatState()) {
                case "No Repeat" -> {
                    if (loadState.getRemainingTime() <= 0) {
                        // Add the duration of the following songs to the remaining time
                        for (int i = playlist.getPlayingSongId() + 1; i < playlist.getSongs().
                                size() && loadState.getRemainingTime() <= 0; i++) {
                            loadState.setRemainingTime(loadState.getRemainingTime()
                                    + playlist.getSongs().get(i).getDuration());
                            if (playlist.getSongs().get(i).equals(playlist.getPlayingSong())) {
                                break;
                            }
                            playlist.setPlayingSong(playlist.getSongs().get(i));
                        }
                    }

                    if (loadState.getRemainingTime() <= 0) {
                        loadState.setRemainingTime(0);
                        loadState.setPlaybackState("pause");
                        loadState.setShuffleState(false);
                    }
                }
                case "Repeat All" -> {
                    while (loadState.getRemainingTime() <= 0) {
                        // Add the duration of the following songs to the remaining time
                        for (int i = playlist.getPlayingSongId() + 1; i < playlist.getSongs().
                                size() && loadState.getRemainingTime() <= 0; i++) {
                            loadState.setRemainingTime(loadState.getRemainingTime()
                                    + playlist.getSongs().get(i).getDuration());
                            if (playlist.getSongs().get(i).equals(playlist.getPlayingSong())) {
                                break;
                            }
                            playlist.setPlayingSong(playlist.getSongs().get(i));
                        }

                        if (loadState.getRemainingTime() <= 0) {
                            loadState.setRemainingTime(loadState.getRemainingTime()
                                    + playlist.getSongs().get(0).getDuration());
                            playlist.setPlayingSong(playlist.getSongs().get(0));
                        }
                    }
                }
                case "Repeat Current Song" -> {
                    while (loadState.getRemainingTime() <= 0) {
                        loadState.setRemainingTime(loadState.getRemainingTime()
                                + playlist.getLoopedSong().getDuration());
                    }
                }
                default -> System.out.println("Invalid repeat state.");
            }
        }
    }
}
