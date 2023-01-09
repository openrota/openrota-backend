package com.shareNwork.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import lombok.Data;

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
