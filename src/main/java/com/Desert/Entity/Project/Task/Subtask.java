package com.Desert.Entity.Project.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Subtask")
@Getter
@Setter
@ToString
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private boolean completed;
    private int order;

    @ManyToOne
    private ProjectTask task;
}
