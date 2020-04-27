package com.Desert.Entity.Music;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PlaylistItem",
            joinColumns = @JoinColumn(name = "playlistID"),
            inverseJoinColumns = @JoinColumn(name = "songID"))
    private List<Song> songList;

    @Override
    public String toString() {
        return name;
    }
}
