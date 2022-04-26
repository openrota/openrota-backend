package com.shareNwork.domain;

import com.shareNwork.domain.constants.ResourceRequestStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourceRequest extends PanacheEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee requester;

    @ManyToOne
    private SharedResource resource;

    private String pillar;

    private String project;

    private String taskDetails;

    private String startDate;

    private String endDate;

    private ResourceRequestStatus status;

    private LocalDateTime createdAt;

    @Getter
    @Setter
    @OneToMany(mappedBy = "resourceRequest", fetch = FetchType.LAZY)
    List<ResourceRequestSkillsProficiency> skillProficiencies;

    public ResourceRequest(Employee requester, String pillar, String project, String taskDetails, String startDate, String endDate, ResourceRequestStatus status) {
        this.requester = requester;
        this.pillar = pillar;
        this.project = project;
        this.taskDetails = taskDetails;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
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
