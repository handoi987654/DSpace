package com.Desert.Entity.Music;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SongType")
@Getter
@Setter
@ToString
public class SongType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @OneToMany(mappedBy = "songType")
    private List<Song> songList;
}
