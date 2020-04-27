package com.Desert.Entity.Music;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@EntityListeners(SongListener.class)
@Table(name = "Song")
@Getter
@Setter
@ToString
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private String artist;
    private String rsrcURL;
    private String imageURL;

    @Transient
    private SongEnumType type;

    @ManyToOne
    private SongType songType;
}

class SongListener {

    @PostLoad
    @PostUpdate
    public final void setEnumType(Song song) {
        song.setType(SongEnumType.valueOf(song.getSongType().getName()));
    }

}
