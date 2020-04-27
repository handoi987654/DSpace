package com.Desert.Entity.IDea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "IDeaTask")
@Getter
@Setter
@ToString
public class IDeaTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String title;
    private boolean completed;
    @Column(name = "`order`")
    private int order;
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "ideaID")
    private IDea iDea;
    @ManyToOne
    @JoinColumn(name = "parentID")
    private IDeaTask parentTask;
    @OneToMany(mappedBy = "parentTask", fetch = FetchType.EAGER)
    private List<IDeaTask> childTasks;
}
