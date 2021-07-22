package com.shareNwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class Skill extends BaseEntity {
    @OneToMany(mappedBy = "skill")
    Set<EmployeeSkillProficiency> skillProficiencies;
    @NotNull
    private String name;
}
