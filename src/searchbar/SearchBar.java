package searchbar;

import main.Library;
import main.Playlist;
import main.Podcast;
import main.Song;
import main.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchBar {
    private static final int MAX_ITEMS = 5;

    /* Search functions */

    /**
     * Search for songs in the library
     *
     * @param searchParams the search filters
     * @param library      the library
     * @return the search results, up to 5
     */
    public ArrayList<Song> searchSong(final HashMap<String, Object> searchParams,
                                      final Library library) {
        ArrayList<Song> songList = library.getSongs();
        if (searchParams == null) {
            System.out.println("ERROR: SearchParams is null");
            return songList;
        }

        // Check songs for all the filters
        if (searchParams.containsKey("name")) {
            songList = searchSongByName((String) searchParams.get("name"), songList);
        }
        if (searchParams.containsKey("album")) {
            songList = searchSongByAlbum((String) searchParams.get("album"), songList);
        }
        if (searchParams.containsKey("tags")) {
            songList = searchSongByTags((ArrayList<String>) searchParams.get("tags"), songList);
        }
        if (searchParams.containsKey("lyrics")) {
            songList = searchSongByLyrics((String) searchParams.get("lyrics"), songList);
        }
        if (searchParams.containsKey("genre")) {
            songList = searchSongByGenre((String) searchParams.get("genre"), songList);
        }
        if (searchParams.containsKey("releaseYear")) {
            String releaseYear = (String) searchParams.get("releaseYear");
            String parameter = releaseYear.substring(0, 1);
            songList = searchSongByReleaseYear(Integer.parseInt(releaseYear.substring(
                    1)), songList, parameter);
        }
        if (searchParams.containsKey("artist")) {
            songList = searchSongByArtist((String) searchParams.get("artist"), songList);
        }

        // Return up to 5 songs
        if (songList.size() > MAX_ITEMS) {
            return new ArrayList<>(songList.subList(0, MAX_ITEMS));
        }

        return songList;
    }

    /**
     * Search for playlists in the library
     *
     * @param searchParams the search filters
     * @param library      the library
     * @return the search results, up to 5
     */
    public ArrayList<Playlist> searchPlaylist(final HashMap<String, Object> searchParams,
                                              final Library library, final User user) {
        ArrayList<Playlist> playlistList = library.getPlaylists();
        if (searchParams == null) {
            return playlistList;
        }

        // Check playlists for all the filters
        playlistList = searchPlaylistByVisibility(playlistList, user);

        if (searchParams.containsKey("name")) {
            playlistList = searchPlaylistByName((String) searchParams.get("name"), playlistList);
        }
        if (searchParams.containsKey("owner")) {
            playlistList = searchPlaylistByOwner((String) searchParams.get("owner"), playlistList);
        }

        // Return up to 5 playlists
        if (playlistList.size() > MAX_ITEMS) {
            return new ArrayList<>(playlistList.subList(0, MAX_ITEMS));
        }

        return playlistList;
    }

    /**
     * Search for podcasts in the library
     *
     * @param searchParams the search filters
     * @param library      the library
     * @return the search results, up to 5
     */
    public ArrayList<Podcast> searchPodcast(final HashMap<String, Object> searchParams,
                                            final Library library) {
        ArrayList<Podcast> podcastList = library.getPodcasts();
        if (searchParams == null) {
            System.out.println("SearchParams is null");
            return podcastList;
        }

        // Check podcasts for all the filters
        if (searchParams.containsKey("name")) {
            podcastList = searchPodcastByName((String) searchParams.get("name"), podcastList);
        }
        if (searchParams.containsKey("owner")) {
            podcastList = searchPodcastByOwner((String) searchParams.get("owner"), podcastList);
        }

        // Return up to 5 podcasts
        if (podcastList.size() > MAX_ITEMS) {
            return new ArrayList<>(podcastList.subList(0, MAX_ITEMS));
        }

        return podcastList;
    }

    /* Select functions */

    /**
     * Select a song from the search results
     *
     * @param songs      the search results
     * @param itemNumber the item number to select
     * @return the selected song
     */
    public Song selectSong(final ArrayList<Song> songs, final int itemNumber) {
        if (itemNumber > songs.size()) {
            return null;
        }

        return songs.get(itemNumber - 1);
    }

    /**
     * Select a playlist from the search results
     *
     * @param playlists  the search results
     * @param itemNumber the item number to select
     * @return the selected playlist
     */
    public Playlist selectPlaylist(final ArrayList<Playlist> playlists,
                                   final int itemNumber) {
        if (itemNumber > playlists.size()) {
            return null;
        }

        return playlists.get(itemNumber - 1);
    }

    /**
     * Select a podcast from the search results
     *
     * @param podcasts   the search results
     * @param itemNumber the item number to select
     * @return the selected podcast
     */
    public Podcast selectPodcast(final ArrayList<Podcast> podcasts,
                                 final int itemNumber) {
        if (itemNumber > podcasts.size()) {
            return null;
        }

        return podcasts.get(itemNumber - 1);
    }

    /* Search helper functions */

    private static ArrayList<Song> searchSongByName(final String name,
                                                    final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            // Verify if song.name start with name
            if (song.getName().startsWith(name)) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByAlbum(final String album, final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            if (song.getAlbum().equals(album)) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByTags(final ArrayList<String> tags,
                                             final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTags().containsAll(tags)) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByLyrics(final String lyrics, final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            // Verify if song.lyrics contains lyrics (case-insensitive)
            if (song.getLyrics().toLowerCase().contains(lyrics.toLowerCase())) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByGenre(final String genre, final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            if (song.getGenre().equalsIgnoreCase(genre)) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByReleaseYear(final Integer releaseYear,
                                                    final ArrayList<Song> songs,
                                                    final String parameter) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            switch (parameter) {
                case "<" -> {
                    if (song.getReleaseYear() < releaseYear) {
                        songList.add(song);
                    }
                }
                case ">" -> {
                    if (song.getReleaseYear() > releaseYear) {
                        songList.add(song);
                    }
                }
                case "=" -> {
                    if (song.getReleaseYear().equals(releaseYear)) {
                        songList.add(song);
                    }
                }
                default -> System.out.println("ERROR: Invalid parameter");
            }
        }

        return songList;
    }

    private ArrayList<Song> searchSongByArtist(final String artist, final ArrayList<Song> songs) {
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs) {
            if (song.getArtist().equals(artist)) {
                songList.add(song);
            }
        }

        return songList;
    }

    private ArrayList<Playlist> searchPlaylistByName(final String name,
                                                     final ArrayList<Playlist> playlists) {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            // Verify if playlist.name start with name
            if (playlist.getName().startsWith(name)) {
                playlistList.add(playlist);
            }
        }

        return playlistList;
    }

    private ArrayList<Playlist> searchPlaylistByOwner(final String owner,
                                                      final ArrayList<Playlist> playlists) {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist.getOwner().equals(owner)) {
                playlistList.add(playlist);
            }
        }

        return playlistList;
    }

    private ArrayList<Playlist> searchPlaylistByVisibility(final ArrayList<Playlist> playlists,
                                                           final User user) {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist.getVisibility().equals("public") || playlist.getOwner().
                    equals(user.getUsername())) {
                playlistList.add(playlist);
            }
        }

        return playlistList;
    }

    private ArrayList<Podcast> searchPodcastByName(final String name,
                                                   final ArrayList<Podcast> podcasts) {
        ArrayList<Podcast> podcastList = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            // Verify if podcast.name start with name
            if (podcast.getName().startsWith(name)) {
                podcastList.add(podcast);
            }
        }

        return podcastList;
    }

    private ArrayList<Podcast> searchPodcastByOwner(final String owner,
                                                    final ArrayList<Podcast> podcasts) {
        ArrayList<Podcast> podcastList = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getOwner().equals(owner)) {
                podcastList.add(podcast);
            }
        }

        return podcastList;
    }
}
