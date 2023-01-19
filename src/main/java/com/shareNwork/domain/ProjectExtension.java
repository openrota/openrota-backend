package com.shareNwork.domain;

import com.shareNwork.domain.constants.ProjectStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class ProjectExtension extends PanacheEntity {

    @ManyToOne
    private Project project;

    private String reasonForExtension;

    private String extendedDate;

    private ProjectStatus status;

    public ProjectExtension(String reasonForExtension, String extendedDate){
        this.reasonForExtension=reasonForExtension;
        this.extendedDate=extendedDate;
    }
}
