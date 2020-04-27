package com.Desert.Entity.Project.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TaskNote")
@Getter
@Setter
@ToString
public class TaskNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String content;
    private int order;

    @ManyToOne
    private ProjectTask task;
}
