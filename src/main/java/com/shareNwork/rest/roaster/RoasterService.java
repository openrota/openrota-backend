package com.shareNwork.rest.roaster;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.Roaster;
import com.shareNwork.domain.SharedResource;
import com.shareNwork.repository.EmployeeRepository;
import com.shareNwork.repository.ProjectRepository;
import com.shareNwork.repository.ResourceRequestRepository;
import com.shareNwork.repository.SharedResourceRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.solver.SolverManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;

import java.util.List;

@ApplicationScoped
public class RoasterService {
    private SolverManager<Roaster, Integer> solverManager;
    private ScoreManager<Roaster, HardMediumSoftLongScore> scoreManager;
    private SharedResourceRepository employeeRepository;
    private ResourceRequestRepository resourceRequestRepository;

    @Inject
    public RoasterService(SolverManager<Roaster, Integer> solverManager, ScoreManager<Roaster, HardMediumSoftLongScore> scoreManager,
                          SharedResourceRepository employeeRepository, ResourceRequestRepository resourceRequestRepository) {
        this.solverManager = solverManager;
        this.scoreManager = scoreManager;
        this.employeeRepository = employeeRepository;
        this.resourceRequestRepository = resourceRequestRepository;
    }
    @GET
    @Transactional
    public Roaster getRoster() {
       return buildRoster(12);
    }

    @Transactional
    public void solveRoster(Integer tenantId) {
        // buildRoaster is called once when solving starts, and it provides the inputs to solving algorithm
        // Save is called multiple times, for every best solution change
        solverManager.solveAndListen(tenantId, this::buildRoster, this::save);
    }

    @Transactional
    public Roaster buildRoster(Integer tenantId) {
        List<SharedResource> employeeList = employeeRepository.findAll().list();

        List<ResourceRequest> resourceRequestList = resourceRequestRepository.findAll().list();

        Roaster roster = new Roaster(employeeList, resourceRequestList);

        scoreManager.updateScore(roster);
        return roster;
    }

    @Transactional
    protected void save(Roaster roaster) {
        for (ResourceRequest resourceRequest : roaster.getResourceRequests()) {
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
            ResourceRequest resourceRequestResult = resourceRequestRepository.findById(resourceRequest.id);
            resourceRequestResult.setSuggestedResource(resourceRequest.getSuggestedResource());
            System.out.println("project ka employee " + resourceRequest.getSuggestedResource().getFirstName());
        }
    }
}
