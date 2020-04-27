package com.Desert.Repository;

import com.Desert.Entity.Music.Playlist;
import com.Desert.Entity.Music.Song;

public interface MusicRepo {

    void insertSong(Song song);

    void updateSong(Song song);

    void deleteSong(Song song);

    void insertPlaylist(Playlist playlist);

    void updatePlaylist(Playlist playlist);

    void deletePlaylist(Playlist playlist);
}
