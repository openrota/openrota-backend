package com.shareNwork.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.shareNwork.domain.constants.CustomEventType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomEvent extends PanacheEntity {

    @NotNull
    private String eventName;
    private String description;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    private CustomEventType customEventType;
    @ManyToOne
    SharedResource employee;
}
