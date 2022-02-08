package com.shareNwork.repository;

import com.shareNwork.domain.*;
import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import com.shareNwork.domain.constants.ResourceRequestStatus;
import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.runtime.StartupEvent;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@ApplicationScoped
public class BootstrapRepo {

    private SkillRepository skillRepository;
    private SharedResourceRepository sharedResourceRepository;
    private ResourceRequestRepository resourceRequestRepository;

    @Inject
    public BootstrapRepo(SkillRepository skillRepository, SharedResourceRepository sharedResourceRepository, ResourceRequestRepository resourceRequestRepository) {
        this.skillRepository = skillRepository;
        this.sharedResourceRepository = sharedResourceRepository;
        this.resourceRequestRepository = resourceRequestRepository;
    }

    @Transactional
    void onStart(@Observes StartupEvent startupEvent) {

        Skill skill1 = new Skill("react");
        Skill skill2 = new Skill("java");
        Skill skill3 = new Skill("html");
        Skill skill4 = new Skill("javascript");

        this.skillRepository.persist(skill1);
        this.skillRepository.persist(skill2);
        this.skillRepository.persist(skill3);
        this.skillRepository.persist(skill4);

        Employee manager1 = new Employee("Imran", "khalidi", "RH21821", "ikhalidi@redhat.com", "Manager");
        SharedResource employee1 = new SharedResource("Rishi", "raj", "RISH323", "ranand@redhat.com", "engineer", "12", ResourceAvailabilityStatus.AVAILABLE);
        SharedResource employee2 = new SharedResource("Abhishek", "kumar", "ABHI323", "ankumr@redhat.com", "engineer", "23", ResourceAvailabilityStatus.UNAVAILABLE);

        this.sharedResourceRepository.persist(employee1);
        this.sharedResourceRepository.persist(employee2);

        manager1.persist();

        ResourceRequest resourceRequest = new ResourceRequest(manager1, "Business Automation", "Kogito Website styling", "a very important one", LocalDate.now(), LocalDate.now(), ResourceRequestStatus.PENDING);
        resourceRequest.persist();

        ResourceRequestSkillsProficiency resourceRequestSkillsProficiency = new ResourceRequestSkillsProficiency(SkillProficiencyLevel.ADVANCED);
        resourceRequestSkillsProficiency.setSkill(skill1);
        resourceRequestSkillsProficiency.setResourceRequest(resourceRequest);
        resourceRequestSkillsProficiency.persist();

        EmployeeSkillProficiency employeeSkillProficiency = new EmployeeSkillProficiency(SkillProficiencyLevel.ADVANCED);
        employeeSkillProficiency.setSkill(skill1);
        employeeSkillProficiency.setEmployee(employee1);
        employeeSkillProficiency.persist();

        EmployeeSkillProficiency employeeSkillProficiency2 = new EmployeeSkillProficiency(SkillProficiencyLevel.BEGINNER);
        employeeSkillProficiency2.setSkill(skill2);
        employeeSkillProficiency2.setEmployee(employee2);
        employeeSkillProficiency2.persist();

        AllowedDesignation allowedDesignation = new AllowedDesignation("Associate Manager, Software Engineering");
        AllowedDesignation allowedDesignation1 = new AllowedDesignation("Manager, Software Engineering");

        allowedDesignation1.persist();
        allowedDesignation.persist();

    }

}
