package com.Desert.Entity.IDea;

import com.Desert.Entity.ResourceEnumType;
import com.Desert.Entity.ResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(ResourceListener.class)
@Table(name = "IDeaResource")
@Getter
@Setter
@ToString
public class IDeaResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String rsrcURL;
    private LocalDateTime addedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private IDea iDea;
    @ManyToOne(fetch = FetchType.LAZY)
    private IDeaResourceGroup group;
    @ManyToOne
    private ResourceType resourceType;

    @Transient
    private ResourceEnumType type;
}

class ResourceListener {

    @PostLoad
    @PostUpdate
    public final void loadEnumType(IDeaResource resource) {
        resource.setType(ResourceEnumType.valueOf(resource.getResourceType().getName()));
    }
}
