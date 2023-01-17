package com.shareNwork.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.shareNwork.domain.constants.ProjectStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Project extends PanacheEntity {

    private String projectName;

    private String businessUnit;

    @OneToOne
    private Slot slot;

    @ManyToOne
    private Employee projectManager;

    @OneToOne
    private ResourceRequest resourcerequest;

    private ProjectStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    SharedResource employee;

//    @Getter
//    @Setter
//    @OneToMany(mappedBy = "project", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProjectSkillsProficiency> skillsProficiencies;

    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    @Setter
    Set<String> skillSet;

    public Project() {
    }

    public Project(String projectName, String businessUnit, ResourceRequest resourceRequest, Slot slot, ProjectStatus status) {
        this.projectName = projectName;
        this.status = status;
        this.businessUnit = businessUnit;
        this.resourcerequest = resourceRequest;
        this.slot = slot;
//        this.createdAt=createdAt;
    }
}
