package com.shareNwork.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SharedResource extends Employee {

    @Getter
    @Setter
    @OneToMany(mappedBy = "employee")
    Set<EmployeeSkillProficiency> skillProficiencies;

    @Getter
    @Setter
    private String totalExperience;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EmployeeSkillProficiencySet",
            joinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skillId", referencedColumnName = "id"))
    private Set<Skill> skillProficiencySet;

    @Getter
    @Setter
    @OneToMany(mappedBy = "sharedResource")
    private Set<EmployeeProjectDetail> employeeProjectDetails;

    public SharedResource(String firstName, String lastName, String employeeId, String emailId, String designation, String totalExperience) {
        super(firstName, lastName, employeeId, emailId, designation);
        this.totalExperience = totalExperience;
    }
}
