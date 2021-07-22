package com.shareNwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
public class EmployeeSkillProficiencyKey implements Serializable {

    @Column(name = "employeeId")
    Long employeeId;

    @Column(name = "skillId")
    Long skillId;

}
