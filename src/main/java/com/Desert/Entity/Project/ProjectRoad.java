package com.Desert.Entity.Project;

import com.Desert.Entity.Project.Task.ProjectTask;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(RoadListener.class)
@Table(name = "ProjectRoad")
@Getter
@Setter
@ToString
public class ProjectRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    @Transient
    private RoadEnumStatus status;

    @ManyToOne
    private RoadStatus roadStatus;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy = "road", fetch = FetchType.EAGER)
    private List<ProjectTask> taskList;
}

class RoadListener {

    @PostLoad
    @PostUpdate
    public final void loadEnumType(ProjectRoad road) {
        road.setStatus(RoadEnumStatus.valueOf(road.getRoadStatus().getName()));
    }
}
