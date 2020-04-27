package com.Desert.Entity.Project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Project")
@Getter
@Setter
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String imageURL;

    @OneToMany(mappedBy = "project")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<ProjectNote> noteList;
    @OneToMany(mappedBy = "project")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<ProjectRoad> roadList;
    @OneToMany(mappedBy = "project")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<ProjectResource> resourceList;

}
