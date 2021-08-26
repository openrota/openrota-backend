package com.shareNwork.domain;

import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectSkillProficiency extends PanacheEntity {

   @ManyToOne
   private Project project;

   @ManyToOne
   private Skill skill;

   private SkillProficiencyLevel proficiencyLevel;

   public ProjectSkillProficiency(SkillProficiencyLevel skillProficiencyLevel) {
      this.proficiencyLevel = skillProficiencyLevel;
   }
}
