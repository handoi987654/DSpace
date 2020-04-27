package com.Desert.Service;

import com.Desert.Entity.Music.Playlist;
import com.Desert.Entity.Music.Song;
import com.Desert.Repository.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MusicServiceBean implements MusicService {

    @Autowired
    private MusicRepo repo;

    @Override
    public void insertSong(Song song) {
        repo.insertSong(song);
    }

    @Override
    public void updateSong(Song song) {
        repo.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        repo.deleteSong(song);
    }

    @Override
    public void insertPlaylist(Playlist playlist) {
        repo.insertPlaylist(playlist);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        repo.updatePlaylist(playlist);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        repo.deletePlaylist(playlist);
    }
}
