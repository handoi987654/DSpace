package com.Desert.Entity.IDea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "IDea")
@Getter
@Setter
@ToString
public class IDea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String imageURL;

    @OneToMany(mappedBy = "iDea")
    private List<IDeaNote> noteList;
    @OneToMany(mappedBy = "iDea")
    private List<IDeaQuestion> questionList;
    @OneToMany(mappedBy = "iDea")
    private List<IDeaResourceGroup> rsrcGroupList;
    @OneToMany(mappedBy = "iDea")
    private List<IDeaTask> taskList;
}
