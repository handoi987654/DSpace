package com.Desert.Entity.Project.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TaskRsrc")
@Getter
@Setter
@ToString
public class TaskRsrc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String rsrcURL;

    @ManyToOne
    private ProjectTask task;
}
