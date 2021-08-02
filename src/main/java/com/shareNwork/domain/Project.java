package com.shareNwork.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class Project extends PanacheEntity {

   private String projectName;

   private String totalExperience;

   @OneToOne
   @JoinColumn(name = "slot_id", referencedColumnName = "id")
   private Slot slot;

   @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
   List<ProjectSkillProficiency> projectSkillProficiencies;

   @OneToOne
   @JoinColumn(name = "manager_id", referencedColumnName = "id")
   Manager manager;


}
