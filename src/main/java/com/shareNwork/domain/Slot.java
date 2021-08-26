package com.shareNwork.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
public class Slot extends BaseEntity {

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToOne(mappedBy = "slot")
    private Project project;

}
