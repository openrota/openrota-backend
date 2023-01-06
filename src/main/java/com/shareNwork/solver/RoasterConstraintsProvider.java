package com.shareNwork.solver;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.ResourceRequest;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.uni.UniConstraintStream;

public final class RoasterConstraintsProvider implements ConstraintProvider {
    private static UniConstraintStream<ResourceRequest> getAssignedRequestConstraintStream(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(ResourceRequest.class) // To match DRL
                .filter(resourceRequest -> resourceRequest.getSuggestedResource() != null);
    }

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                requiredSkillsForProject(constraintFactory), 
        };
    }

    Constraint requiredSkillsForProject(ConstraintFactory constraintFactory) {
        return getAssignedRequestConstraintStream(constraintFactory)
                .filter(resourceRequest -> !resourceRequest.hasRequiredSkills())
                .penalize(RoasterConstraintConfiguration.CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT, HardMediumSoftLongScore.ONE_HARD);
    }
}
