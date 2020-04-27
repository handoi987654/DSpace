package com.Desert.Entity;

import com.Desert.Entity.IDea.IDeaResource;
import com.Desert.Entity.Project.ProjectResource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ResourceType")
@Getter
@Setter
@ToString
public class ResourceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;

    @OneToMany(mappedBy = "resourceType")
    private List<IDeaResource> iDeaResourceList;
    @OneToMany(mappedBy = "resourceType")
    private List<ProjectResource> projectResourceList;
}
