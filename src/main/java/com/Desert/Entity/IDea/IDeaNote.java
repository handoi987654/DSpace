package com.Desert.Entity.IDea;

import com.Desert.Control.Note;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "IDeaNote")
@Getter
@Setter
@ToString
public class IDeaNote extends Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private IDea iDea;
}
