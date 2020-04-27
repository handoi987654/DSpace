package com.Desert.Entity.Project;

import com.Desert.Entity.ResourceEnumType;
import com.Desert.Entity.ResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(ResourceListener.class)
@Table(name = "ProjectResource")
@Getter
@Setter
@ToString
public class ProjectResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String name;
    private String rsrcURL;
    private LocalDateTime addedDateTime;

    @Transient
    private ResourceEnumType type;

    @ManyToOne
    private Project project;
    @ManyToOne
    private ResourceType resourceType;
}

class ResourceListener {

    @PostLoad
    @PostUpdate
    public final void setEnumType(ProjectResource resource) {
        resource.setType(ResourceEnumType.valueOf(resource.getResourceType().getName()));
    }
}
