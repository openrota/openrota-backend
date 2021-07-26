package com.shareNwork.solver;

import com.shareNwork.domain.Project;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.uni.UniConstraintStream;

public final class RoasterConstraintsProvider implements ConstraintProvider {
    private static UniConstraintStream<Project> getAssignedProjectConstraintStream(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Project.class) // To match DRL
                .filter(shift -> shift.getEmployee() != null);
    }

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                requiredSkillsForProject(constraintFactory),
        };
    }

    Constraint requiredSkillsForProject(ConstraintFactory constraintFactory) {
        return getAssignedProjectConstraintStream(constraintFactory)
                .filter(project -> !project.hasRequiredSkills())
                .penalize(RoasterConstraintConfiguration.CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT, HardMediumSoftLongScore.ONE_HARD);
//                .penalizeConfigurableLong(RoasterConstraintConfiguration.CONSTRAINT_REQUIRED_SKILL_FOR_A_PROJECT, Project::getLengthInMinutes);
    }
}
