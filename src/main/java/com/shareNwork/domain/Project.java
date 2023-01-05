package com.shareNwork.domain;

import com.shareNwork.domain.constants.ProjectStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Project extends PanacheEntity {

    @NotNull
    private String projectName;

    @NotNull
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
    public Project(String projectName, String businessUnit, ResourceRequest resourcerequest) {
        this.projectName=projectName;
        this.businessUnit=businessUnit;
        this.resourcerequest=resourcerequest;
//        this.createdAt=createdAt;
    }

}
