package com.shareNwork.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Slot extends PanacheEntity {

    private String startDate;

    private String endDate;

    public Slot(String startDate, String endDate){
        this.startDate=startDate;
        this.endDate=endDate;
    }

}
