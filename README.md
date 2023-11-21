# README Music Player

<div align="center"><img src="https://media.tenor.com/XuaFiOk-2gcAAAAC/dreamy-bull.gif" width="300px"></div>

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
Each command must be called in a certain way.

### Search
Search for songs, playlists, or podcasts based on specified filters.
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

<div align="center"><img src="https://media.tenor.com/c3xvaQpdxZ8AAAAd/kkatmane.gif" width="500px"></div>
