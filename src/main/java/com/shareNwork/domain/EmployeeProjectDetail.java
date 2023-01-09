package com.shareNwork.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class EmployeeProjectDetail extends PanacheEntity {

    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    private SharedResource sharedResource;
}
