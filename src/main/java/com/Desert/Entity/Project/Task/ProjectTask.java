package com.Desert.Entity.Project.Task;

import com.Desert.Entity.Project.ProjectRoad;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(ProjectTaskListener.class)
@Table(name = "ProjectTask")
@Getter
@Setter
@ToString
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private LocalDateTime deadline;
    private int order;
    private LocalDateTime createdDateTime;

    @Transient
    private TaskEnumStatus status;

    @ManyToOne
    private TaskStatus taskStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectRoad road;

    @OneToMany(mappedBy = "task")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Subtask> subtaskList;
    @OneToMany(mappedBy = "task")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<TaskNote> noteList;
    @OneToMany(mappedBy = "task")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<TaskRsrc> rsrcList;
}

class ProjectTaskListener {

    @PostLoad
    @PostUpdate
    public final void loadEnumStatus(ProjectTask task) {
        task.setStatus(TaskEnumStatus.valueOf(task.getTaskStatus().getName()));
    }
}
