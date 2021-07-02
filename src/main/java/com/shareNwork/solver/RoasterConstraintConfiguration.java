package com.shareNwork.solver;

import com.shareNwork.domain.BaseEntity;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

import javax.persistence.Entity;

@Entity
@ConstraintConfiguration(constraintPackage = "com.shareNwork.solver")
public class RoasterConstraintConfiguration extends BaseEntity {
    public static final String CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT = "Required skill for a shift";

    @ConstraintWeight(CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT)
    private HardMediumSoftLongScore requiredSkill = HardMediumSoftLongScore.ofHard(100);

}
