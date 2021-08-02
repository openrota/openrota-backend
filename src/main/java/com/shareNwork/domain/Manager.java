package com.shareNwork.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Manager extends Employee {

   @OneToOne(mappedBy = "manager")
   private Project project;

   @OneToMany(mappedBy = "manager")
   private Set<ManagerProjectDetail> managerProjectDetails;

}
