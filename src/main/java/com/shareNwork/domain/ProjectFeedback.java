package com.shareNwork.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ProjectFeedback extends PanacheEntity {

    @OneToOne
    private Project project;

    private String feedback;

    private LocalDateTime creationDate;

    public ProjectFeedback(String feedback, LocalDateTime creationDate) {
        this.feedback = feedback;
        this.creationDate = creationDate;
    }
}
