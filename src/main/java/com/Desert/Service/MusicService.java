package com.Desert.Service;

import com.Desert.Entity.Music.Playlist;
import com.Desert.Entity.Music.Song;

public interface MusicService {

    void insertSong(Song song);

    void updateSong(Song song);

    void deleteSong(Song song);

    void insertPlaylist(Playlist playlist);

    void updatePlaylist(Playlist playlist);

    void deletePlaylist(Playlist playlist);
}
