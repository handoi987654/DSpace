package com.Desert.Entity.Project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RoadStatus")
@Getter
@Setter
@ToString
public class RoadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @OneToMany(mappedBy = "roadStatus")
    private List<ProjectRoad> roadList;
}
