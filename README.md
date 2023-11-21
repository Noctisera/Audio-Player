# Audio Player

<div align="center"><img src="https://media.tenor.com/llgchsljWEcAAAAd/catvibe.gif" width="300px"></div>

## Description

This project simulates a music platform with functionalities similar to Spotify. Users can perform various actions, and the system generates reports for administrators. The simulation is driven by commands provided in JSON files.

## Usage

### Search Bar
The search bar allows users to search for songs, playlists, and podcasts based on multiple filters. Filters include name, album, tags, lyrics, genre, release year, and artist.

### Music Player
The music player can play audio files from the library or playlists. Podcasts remember the last played episode and resume from that point.

### User
The platform supports multiple users, each with a unique username. Users can interact with the search bar, player, and create and manage playlists.

### Timestamp
Commands include a timestamp to simulate real-time execution. The timestamp indicates when a command is executed relative to the start of the simulation.

## Commands
Here are descriptions and examples for each command.

### Search
Search for songs, playlists, or podcasts based on specified filters and returns the first 5 results.
```json
{
    "command": "search",
    "username": "alice22",
    "timestamp": 10,
    "type": "song",
    "filters": {
        "name": "Sta"
    }
}
```

### Select
Select an option from the last search results.
```json
{
    "command": "select",
    "username": "alice22",
    "timestamp": 15,
    "itemNumber": 1
}
```

### Load
Load a selected audio source into the player.
```json
{
    "command": "load",
    "username": "alice22",
    "timestamp": 20
}
```

### PlayPause
Toggle between play and pause states.
```json
{
    "command": "playPause",
    "username": "alice22",
    "timestamp": 30
}
```

### Repeat
Toggle between "No Repeat", "Repeat Once" and "Repeat Infinite" states for songs and podcasts, and between "No Repeat", "Repeat All" and "Repeat Current Song" states for playlists.
```json
{
    "command": "repeat",
    "username": "alice22",
    "timestamp": 31
}
```

### Shuffle
Enable or disable the shuffle function. A seed for the random class must also be provided for enabling shuffle.
```json
{
    "command": "shuffle",
    "username": "bob35",
    "timestamp": 850,
    "seed": 1024
}
```

### Forward/Backward
Backs or fastforwads the current episode of the podcast by 90 seconds.
```json
{
    "command" : "forward",
    "user" : "bob35",
    "timestamp" : 1050
}
```
```json
{
    "command": "backward",
    "username": "bob35",
    "timestamp": 1390
}
```

### Like
Like the loaded song.
```json
{
    "command": "like",
    "username": "bob35",
    "timestamp": 205
}
```

### Next/Prev
Goes forward or backward by 1 track.
```json
{
    "command": "next",
    "username": "bob35",
    "timestamp": 590
}
```
```json
{
    "command": "prev",
    "username": "bob35",
    "timestamp": 710
}
```

### AddRemoveInPlaylist
Add the loaded song to the specified playlist of the user or remove it if the playlist already contains it.
```json
{
    "command": "addRemoveInPlaylist",
    "username": "alice22",
    "timestamp": 24,
    "playlistId": 1
}
```

### Status
Retrieve and display the current status of the loaded track.
```json
{
    "command": "status",
    "username": "alice22",
    "timestamp": 59
}
```

### CreatePlaylist
Create a new empty public playlist.
```json
{
    "command": "createPlaylist",
    "username": "alice22",
    "timestamp": 5,
    "playlistName": "Playlist bengos"
}
```

### SwitchVisibility
Switch between the public and private states of a playlist.
```json
{
    "command": "switchVisibility",
    "username": "carol19",
    "timestamp": 1130,
    "playlistId": 100
}
```

### FollowPlaylist
Follow a playlist or unfollow previously followed playlist.
```json
{
    "command": "follow",
    "username": "carol19",
    "timestamp": 1050
}
```

### ShowPlaylists
Display all the owned playlists.
```json
{
    "command": "showPlaylists",
    "username": "alice22",
    "timestamp": 65
}
```

### ShowPreferredSongs
Display a list of all liked songs.
```json
{
    "command": "showPreferredSongs",
    "username": "carol19",
    "timestamp": 1000
}
```

### GetTop5Songs
Retrieve and display a list of the 5 most liked songs from the library.
```json
{
    "command": "getTop5Songs",
    "timestamp": 3300
}
```

### GetTop5Playlists
Retrieve and display a list of the 5 most followed playlists.
```json
{
    "command": "getTop5Playlists",
    "timestamp": 2560
}
```

## Reports
### Report Files
All generated reports are provided in Json files.

<div align="center"><img src="https://media.tenor.com/AxfkbmY9MVkAAAAC/cat-vibe-cat-dance.gif" width="500px"></div>
