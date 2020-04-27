package com.Desert.Entity.Project;

import com.Desert.Control.Note;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProjectNote")
@Getter
@Setter
@ToString
public class ProjectNote extends Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
}
