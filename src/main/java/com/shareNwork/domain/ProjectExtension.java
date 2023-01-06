package com.shareNwork.domain;

import com.shareNwork.domain.Project;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ProjectExtension extends PanacheEntity {

    @OneToOne
    private Project project;

    private String reasonForExtension;

    private LocalDateTime extendedDate;

    public ProjectExtension(String reasonForExtension, LocalDateTime extendedDate){
        this.reasonForExtension=reasonForExtension;
        this.extendedDate=extendedDate;
    }
}
