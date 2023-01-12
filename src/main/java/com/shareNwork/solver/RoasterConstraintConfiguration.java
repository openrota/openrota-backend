package com.shareNwork.solver;

import javax.persistence.Entity;

import com.shareNwork.domain.BaseEntity;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

@Entity
@ConstraintConfiguration(constraintPackage = "com.shareNwork.solver")
public class RoasterConstraintConfiguration extends BaseEntity {

    public static final String CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT = "Required skill for a shift";

    @ConstraintWeight(CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT)
    private HardMediumSoftLongScore requiredSkill = HardMediumSoftLongScore.ofHard(100);
}
