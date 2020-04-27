package com.Desert.Entity.IDea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "IDeaResourceGroup")
@Getter
@Setter
@ToString
public class IDeaResourceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @OneToMany(mappedBy = "group")
    private List<IDeaResource> resourceList;
    @ManyToOne
    private IDea iDea;

}
