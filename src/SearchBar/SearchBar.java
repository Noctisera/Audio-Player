package SearchBar;

import main.Library;
import main.Playlist;
import main.Podcast;
import main.Song;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchBar {
//    Uncomment to make SearchBar a singleton
//    private static SearchBar instance;
//
//    private SearchBar() {
//    }
//
//    public static SearchBar getInstance() {
//        if (instance == null) {
//            instance = new SearchBar();
//        }
//
//        return instance;
//    }

    /* Search functions */

    /**
     * Search for songs in the library
     *
     * @param searchParams the search filters
     * @param library      the library
     * @return the search results, up to 5
     */
    public ArrayList<Song> SearchSong(HashMap<String, Object> searchParams, Library library) {
        ArrayList<Song> songList = library.songs;
        if (searchParams == null) {
            System.out.println("ERROR: SearchParams is null");
            return songList;
        }

        // Check songs for all the filters
        if (searchParams.containsKey("name")) {
            songList = SearchSongByName((String) searchParams.get("name"), songList);
        }
        if (searchParams.containsKey("album")) {
            songList = SearchSongByAlbum((String) searchParams.get("album"), songList);
        }
        if (searchParams.containsKey("tags")) {
            songList = SearchSongByTags((ArrayList<String>) searchParams.get("tags"), songList);
        }
        if (searchParams.containsKey("lyrics")) {
            songList = SearchSongByLyrics((String) searchParams.get("lyrics"), songList);
        }
        if (searchParams.containsKey("genre")) {
            songList = SearchSongByGenre((String) searchParams.get("genre"), songList);
        }
        if (searchParams.containsKey("releaseYear")) {
            String releaseYear = (String) searchParams.get("releaseYear");
            String parameter = releaseYear.substring(0, 1);
            songList = SearchSongByReleaseYear(Integer.parseInt(releaseYear.substring(1)), songList, parameter);
        }
        if (searchParams.containsKey("artist")) {
            songList = SearchSongByArtist((String) searchParams.get("artist"), songList);
        }

        // Return up to 5 songs
        if (songList.size() > 5) {
            return new ArrayList<>(songList.subList(0, 5));
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
    public ArrayList<Playlist> SearchPlaylist(HashMap<String, Object> searchParams, Library library) {
        ArrayList<Playlist> playlistList = library.playlists;
        if (searchParams == null) {
            System.out.println("SearchParams is null");
            return playlistList;
        }

        // Check playlists for all the filters
        playlistList = SearchPlaylistByVisibility(playlistList);
        if (searchParams.containsKey("name")) {
            playlistList = SearchPlaylistByName((String) searchParams.get("name"), playlistList);
        }
        if (searchParams.containsKey("owner")) {
            playlistList = SearchPlaylistByOwner((String) searchParams.get("owner"), playlistList);
        }

        // Return up to 5 playlists
        if (playlistList.size() > 5) {
            return new ArrayList<>(playlistList.subList(0, 5));
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
    public ArrayList<Podcast> SearchPodcast(HashMap<String, Object> searchParams, Library library) {
        ArrayList<Podcast> podcastList = library.podcasts;
        if (searchParams == null) {
            System.out.println("SearchParams is null");
            return podcastList;
        }

        // Check podcasts for all the filters
        if (searchParams.containsKey("name")) {
            podcastList = SearchPodcastByName((String) searchParams.get("name"), podcastList);
        }
        if (searchParams.containsKey("owner")) {
            podcastList = SearchPodcastByOwner((String) searchParams.get("owner"), podcastList);
        }

        // Return up to 5 podcasts
        if (podcastList.size() > 5) {
            return new ArrayList<>(podcastList.subList(0, 5));
        }

        return podcastList;
    }

    /* Select functions */

    public Song SelectSong(ArrayList<Song> songs, int itemNumber) {
        if (itemNumber > songs.size()) {
            return null;
        }

        return songs.get(itemNumber - 1);
    }

    public Playlist SelectPlaylist(ArrayList<Playlist> playlists, int itemNumber) {
        if (itemNumber > playlists.size()) {
            return null;
        }

        return playlists.get(itemNumber - 1);
    }

    public Podcast SelectPodcast(ArrayList<Podcast> podcasts, int itemNumber) {
        if (itemNumber > podcasts.size()) {
            return null;
        }

        return podcasts.get(itemNumber - 1);
    }

    /* Search helper functions */

    private static ArrayList<Song> SearchSongByName(String name, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            // Verify if song.name start with name
            if (song.name.startsWith(name)) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByAlbum(String album, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            if (song.album.equals(album)) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByTags(ArrayList<String> tags, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            if (song.tags.containsAll(tags)) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByLyrics(String lyrics, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            // Verify if song.lyrics contains lyrics (case-insensitive)
            if (song.lyrics.toLowerCase().contains(lyrics.toLowerCase())) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByGenre(String genre, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            if (song.genre.equalsIgnoreCase(genre)) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByReleaseYear(Integer releaseYear, ArrayList<Song> songs, String parameter) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            switch (parameter) {
                case "<" -> {
                    if (song.releaseYear < releaseYear) {
                        SongList.add(song);
                    }
                }
                case ">" -> {
                    if (song.releaseYear > releaseYear) {
                        SongList.add(song);
                    }
                }
                case "=" -> {
                    if (song.releaseYear.equals(releaseYear)) {
                        SongList.add(song);
                    }
                }
            }
        }

        return SongList;
    }

    private ArrayList<Song> SearchSongByArtist(String artist, ArrayList<Song> songs) {
        ArrayList<Song> SongList = new ArrayList<>();
        for (Song song : songs) {
            if (song.artist.equals(artist)) {
                SongList.add(song);
            }
        }

        return SongList;
    }

    private ArrayList<Playlist> SearchPlaylistByName(String name, ArrayList<Playlist> playlists) {
        ArrayList<Playlist> PlaylistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            // Verify if playlist.name start with name
            if (playlist.name.startsWith(name)) {
                PlaylistList.add(playlist);
            }
        }

        return PlaylistList;
    }

    private ArrayList<Playlist> SearchPlaylistByOwner(String owner, ArrayList<Playlist> playlists) {
        ArrayList<Playlist> PlaylistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist.owner.equals(owner)) {
                PlaylistList.add(playlist);
            }
        }

        return PlaylistList;
    }

    private ArrayList<Playlist> SearchPlaylistByVisibility(ArrayList<Playlist> playlists) {
        ArrayList<Playlist> PlaylistList = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist.visibility.equals("public")) {
                PlaylistList.add(playlist);
            }
        }

        return PlaylistList;
    }

    private ArrayList<Podcast> SearchPodcastByName(String name, ArrayList<Podcast> podcasts) {
        ArrayList<Podcast> PodcastList = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            // Verify if podcast.name start with name
            if (podcast.name.startsWith(name)) {
                PodcastList.add(podcast);
            }
        }

        return PodcastList;
    }

    private ArrayList<Podcast> SearchPodcastByOwner(String owner, ArrayList<Podcast> podcasts) {
        ArrayList<Podcast> PodcastList = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.owner.equals(owner)) {
                PodcastList.add(podcast);
            }
        }

        return PodcastList;
    }
}
