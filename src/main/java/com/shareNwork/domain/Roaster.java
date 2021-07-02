package com.shareNwork.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

import java.util.List;

@Data
@NoArgsConstructor
@PlanningSolution
public class Roaster extends BaseEntity {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "employeeRange")
    private List<Employee> employeeList;

    @PlanningEntityCollectionProperty
    private List<Project> projectList;

    @PlanningScore
    private HardMediumSoftLongScore score = null;

    public Roaster(List<Employee> employeeList, List<Project> projectList) {
        this.employeeList = employeeList;
        this.projectList = projectList;
    }
}
