package com.Desert.Entity.Project.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TaskStatus")
@Getter
@Setter
@ToString
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @OneToMany(mappedBy = "taskStatus")
    private List<ProjectTask> tasks;
}
