package com.shareNwork.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@PlanningEntity
public class Project extends PanacheEntity {

   private String projectName;

   private String totalExperience;

   @OneToOne
   @JoinColumn(name = "slot_id", referencedColumnName = "id")
   private Slot slot;

   @OneToMany(mappedBy = "project")
   List<ProjectSkillProficiency> projectSkillProficiencies;

   @OneToOne
   @JoinColumn(name = "manager_id", referencedColumnName = "id")
   Manager manager;


}
