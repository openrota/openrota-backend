package com.shareNwork.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Skill extends BaseEntity {
    @NotNull
    private String name;
}
