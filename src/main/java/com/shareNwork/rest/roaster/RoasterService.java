package com.shareNwork.rest.roaster;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.Roaster;
import com.shareNwork.repository.EmployeeRepository;
import com.shareNwork.repository.ProjectRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.solver.SolverManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RoasterService {
    private SolverManager<Roaster, Integer> solverManager;
    private ScoreManager<Roaster, HardMediumSoftLongScore> scoreManager;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    @Inject
    public RoasterService(SolverManager<Roaster, Integer> solverManager, ScoreManager<Roaster, HardMediumSoftLongScore> scoreManager,
                          EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.solverManager = solverManager;
        this.scoreManager = scoreManager;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void solveRoster(Integer tenantId) {
        // buildRoaster is called once when solving starts and it provides the inputs to solving algorithm
        // Save is called multiple times, for every best solution change
        solverManager.solveAndListen(tenantId, this::buildRoster, this::save);
    }

    @Transactional
    public Roaster buildRoster(Integer tenantId) {
        List<Employee> employeeList = employeeRepository.findAll().list();

        List<Project> projectList = projectRepository.findAll().list();

        Roaster roster = new Roaster(employeeList, projectList);

        scoreManager.updateScore(roster);
        return roster;
    }

    @Transactional
    protected void save(Roaster roaster) {
        for (Project project : roaster.getProjectList()) {
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
            Project projectResult = projectRepository.findById(project.id);
            projectResult.setManager(project.getManager());
        }
    }
}
