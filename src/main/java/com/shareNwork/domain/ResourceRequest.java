package com.shareNwork.domain;

import com.shareNwork.domain.constants.ResourceRequestStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@PlanningEntity
public class ResourceRequest extends PanacheEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee requester;

    @ManyToOne
    private SharedResource resource;
    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "employeeRange")
    private SharedResource suggestedResource;
    private String businessUnit;

    private String project;

    private String taskDetails;

    private String startDate;

    private String endDate;

    private ResourceRequestStatus status;

    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> skillSet;
//    @Getter
//    @Setter
//    @OneToMany(mappedBy = "resourceRequest", fetch = FetchType.LAZY)
//    List<ResourceRequestSkillsProficiency> skillProficiencies;

    public ResourceRequest(Employee requester, String businessUnit, String project, String taskDetails, String startDate, String endDate, ResourceRequestStatus status) {
        this.requester = requester;
        this.businessUnit = businessUnit;
        this.project = project;
        this.taskDetails = taskDetails;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    public boolean hasRequiredSkills() {
        return suggestedResource.getSkillSet().containsAll(skillSet);
    }
    // TODO unsupported GraphQL Scalar date types
    public LocalDate getStartLocalDate() {
        return getParsedDate(getEndDate());
    }

    // TODO unsupported GraphQL Scalar date types
    public LocalDate getEndLocalDate() {
        return getParsedDate(getEndDate());
    }

    // TODO workaround for unsupported GraphQL Scalar date types
    private LocalDate getParsedDate(final String date) {
        return LocalDate.ofInstant(Instant.parse(date), ZoneId.of(ZoneOffset.UTC.getId()));
    }
}
