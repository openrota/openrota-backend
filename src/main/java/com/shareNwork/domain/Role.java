package com.shareNwork.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.shareNwork.domain.constants.RoleType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends PanacheEntity {

    private RoleType RoleName;

    private String description;
}
