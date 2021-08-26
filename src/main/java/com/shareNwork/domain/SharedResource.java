package com.shareNwork.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SharedResource extends Employee {

    @Getter
    @Setter
    @OneToMany(mappedBy = "employee")
    List<EmployeeSkillProficiency> skillProficiencies;

    @Getter
    @Setter
    private String totalExperience;

    @Getter
    @Setter
    @OneToMany(mappedBy = "sharedResource")
    private Set<EmployeeProjectDetail> employeeProjectDetails;

    public SharedResource(String firstName, String lastName, String employeeId, String emailId, String designation, String totalExperience) {
        super(firstName, lastName, employeeId, emailId, designation);
        this.totalExperience = totalExperience;
    }
}
