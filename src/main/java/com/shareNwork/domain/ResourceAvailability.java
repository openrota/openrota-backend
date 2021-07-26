package com.shareNwork.domain;

import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class ResourceAvailability extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    private LocalDate startDate;

    private LocalDate endDate;

    private ResourceAvailabilityStatus resourceAvailabilityStatus;

}
