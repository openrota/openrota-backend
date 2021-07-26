package com.shareNwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode
public class EmployeeProjectDetail extends BaseEntity {

    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    private SharedResource sharedResource;

}
