package com.Desert.Repository;

import com.Desert.Entity.Music.Playlist;
import com.Desert.Entity.Music.Song;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MusicRepoBean implements MusicRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insertSong(Song song) {
        Session session = sessionFactory.getCurrentSession();
        session.save(song);
    }

    @Override
    public void updateSong(Song song) {
        Session session = sessionFactory.getCurrentSession();
        session.update(song);
    }

    @Override
    public void deleteSong(Song song) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(song);
    }

    @Override
    public void insertPlaylist(Playlist playlist) {
        Session session = sessionFactory.getCurrentSession();
        session.save(playlist);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        Session session = sessionFactory.getCurrentSession();
        session.update(playlist);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(playlist);
    }
}
