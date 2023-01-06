package com.shareNwork.domain;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.constants.ProjectStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ProjectExtension extends PanacheEntity {

    @ManyToOne
    private Project project;

    private String reasonForExtension;

    private LocalDateTime extendedDate;

    private ProjectStatus status;

    public ProjectExtension(String reasonForExtension, LocalDateTime extendedDate){
        this.reasonForExtension=reasonForExtension;
        this.extendedDate=extendedDate;
    }
}
