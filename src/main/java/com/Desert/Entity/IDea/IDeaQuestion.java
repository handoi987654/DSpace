package com.Desert.Entity.IDea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "IDeaQuestion")
@Getter
@Setter
@ToString
public class IDeaQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private IDea iDea;
    @OneToMany(mappedBy = "question")
    private List<QuestionOption> options;
}
