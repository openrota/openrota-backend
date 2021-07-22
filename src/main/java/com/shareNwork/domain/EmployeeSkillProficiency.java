package com.shareNwork.domain;

import com.shareNwork.domain.constants.SkillProficiencyLevel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EmployeeSkillProficiency {

    @EmbeddedId
    EmployeeSkillProficiencyKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private SharedResource employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private SkillProficiencyLevel proficiencyLevel;

}
