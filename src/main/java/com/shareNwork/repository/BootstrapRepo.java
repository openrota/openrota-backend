package com.shareNwork.repository;

import com.shareNwork.domain.*;
import com.shareNwork.domain.constants.*;
import com.shareNwork.domain.processEngine.Process;
import io.quarkus.runtime.StartupEvent;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
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

        Role role = new Role(RoleType.ADMIN, "Can view all screens and has access to all features of the application");
        Role role1 = new Role(RoleType.REQUESTOR, "Can request resource and track statuses of their requests");
        Role role2 = new Role(RoleType.RESOURCE, "They only have access to personalised screen containing their project information");
        Role role3 = new Role(RoleType.MANAGER, "They only access to information of all associates and their schedule");
        role.persist();
        role1.persist();
        role2.persist();
        role3.persist();

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
        SharedResource employee2 = new SharedResource("Abhishek", "kumar", "ABHI323", "abkuma@redhat.com", "engineer", "23", ResourceAvailabilityStatus.UNAVAILABLE);
        employee1.setRoles(Set.of(role));
        employee2.setRoles(Set.of(role2));
        manager1.setRoles(Set.of(role3));
        this.sharedResourceRepository.persist(employee1);
        this.sharedResourceRepository.persist(employee2);

        manager1.persist();

        ResourceRequest resourceRequest = new ResourceRequest(manager1, "Business Automation", "Kogito Website styling", "a very important one", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
        resourceRequest.setResource(employee2);
        resourceRequest.persist();

        ResourceRequest resourceRequest2 = new ResourceRequest(manager1, "Integration", "Serverless workflow", "Documentation", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
//        resourceRequest2.setResource(employee1);
        resourceRequest2.persist();

        AccessRequest accessRequest = new AccessRequest("ranand@redhat.com", InvitationStatus.PENDING, "Temporary web site building work");
        accessRequest.persist();

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

        Process process1 = new Process("Project Request");
        Process process2 = new Process("Candidate Invitations");
        Process process3 = new Process("Access Request");
        process1.persist();
        process2.persist();
        process3.persist();

    }

}
