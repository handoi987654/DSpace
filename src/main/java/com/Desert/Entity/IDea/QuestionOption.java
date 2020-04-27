package com.Desert.Entity.IDea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "QuestionOption")
@Getter
@Setter
@ToString
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String content;
    private boolean chose;

    @ManyToOne
    private IDeaQuestion question;
}
