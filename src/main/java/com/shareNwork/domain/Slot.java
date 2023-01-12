package com.shareNwork.domain;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Slot extends PanacheEntity {

    private String startDate;

    private String endDate;

    public Slot(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
