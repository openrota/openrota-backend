package com.shareNwork.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
public class Employee extends BaseEntity {

    @NotNull
    private String name;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EmployeeSkillProficiencySet",
            joinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skillId", referencedColumnName = "id"))
    private Set<Skill> skillSet;
}
