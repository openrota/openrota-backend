package com.shareNwork.domain;

import java.util.List;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

@Data
@PlanningSolution
public class Roaster extends BaseEntity {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "employeeRange")
    private List<SharedResource> employeeList;

    @PlanningEntityCollectionProperty
    private List<ResourceRequest> resourceRequests;

    @PlanningScore
    private HardMediumSoftLongScore score = null;

    public Roaster() {
    }

    public Roaster(List<SharedResource> employeeList, List<ResourceRequest> resourceRequests) {
        this.employeeList = employeeList;
        this.resourceRequests = resourceRequests;
    }
}
