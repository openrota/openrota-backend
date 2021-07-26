package com.shareNwork.domain;

import lombok.Data;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PlanningId
    private Long id;
}
