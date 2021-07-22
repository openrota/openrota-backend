package com.shareNwork.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
public class SharedResource extends Employee {

    @OneToMany(mappedBy = "employee")
    Set<EmployeeSkillProficiency> skillProficiencies;

    private String totalExperience;

    public SharedResource(@NotNull String firstName, String lastName, String employeeId, String emailId, String designation, Set<EmployeeSkillProficiency> skillProficiencies, String totalExperience) {
        super(firstName, lastName, employeeId, emailId, designation);
        this.skillProficiencies = skillProficiencies;
        this.totalExperience = totalExperience;
    }

    public Set<EmployeeSkillProficiency> getSkillProficiencies() {
        return skillProficiencies;
    }

    public void setSkillProficiencies(Set<EmployeeSkillProficiency> skillProficiencies) {
        this.skillProficiencies = skillProficiencies;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }
}
