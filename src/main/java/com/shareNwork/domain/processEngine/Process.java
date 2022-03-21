package com.shareNwork.domain.processEngine;

import com.shareNwork.domain.EmployeeSkillProficiency;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Process extends PanacheEntity {

    private String processName;

    @OneToMany(mappedBy = "process", fetch = FetchType.LAZY)
    List<ProcessField> processFields;


    @OneToMany(mappedBy = "process", fetch = FetchType.LAZY)
    List<ProcessAction> processActions;

    public Process(String processName) {
        this.processName = processName;
    }
}
