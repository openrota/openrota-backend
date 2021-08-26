package com.shareNwork.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode
public class ManagerProjectDetail extends PanacheEntity {

   private String projectName;

   @ManyToOne(fetch = FetchType.LAZY)
   private Manager manager;
}
