package com.shareNwork.domain;

import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmployeeSkillProficiency  extends PanacheEntity {

    @ManyToOne
    private SharedResource employee;

    @ManyToOne
    private Skill skill;

    private SkillProficiencyLevel proficiencyLevel;

    public EmployeeSkillProficiency(SkillProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
