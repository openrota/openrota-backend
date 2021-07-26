package com.shareNwork.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
public class Slot extends BaseEntity {

    private LocalDate startDate;

    private LocalDate endDate;

}
