package com.shareNwork.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class Skill extends BaseEntity {

    private String name;

    public Skill(String name) {
        this.name = name;
    }
}
