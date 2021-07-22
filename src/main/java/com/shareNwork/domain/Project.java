package com.shareNwork.domain;

import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@PlanningEntity
public class Project extends BaseEntity {

    @NotNull
    private String projectName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "employeeRange", nullable = true)
    private SharedResource employee = null;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ShiftRequiredSkillSet",
            joinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skillId", referencedColumnName = "id"))
    private Set<Skill> requiredSkillSet;

    public boolean hasRequiredSkills() {
        return employee.getSkillProficiencies().containsAll(requiredSkillSet);
    }

    public long getLengthInMinutes() {
        return startDate.getMonthValue();
    }
}
