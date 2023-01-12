package com.shareNwork.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectSkillsProficiency extends PanacheEntity {

    @ManyToOne
    private Project project;

    @ManyToOne
    private Skill skill;

    private SkillProficiencyLevel proficiencyLevel;

    public ProjectSkillsProficiency(SkillProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public ProjectSkillsProficiency(Skill skill, SkillProficiencyLevel proficiencyLevel) {
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
    }
}
