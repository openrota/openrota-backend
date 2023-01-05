package com.shareNwork.repository;

import com.shareNwork.domain.*;
import com.shareNwork.domain.constants.InvitationStatus;
import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import com.shareNwork.domain.constants.ResourceRequestStatus;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.domain.constants.SkillProficiencyLevel;
import com.shareNwork.domain.constants.*;
import com.shareNwork.domain.processEngine.Process;
import io.quarkus.runtime.StartupEvent;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        Skill skill1 = new Skill("python");
        Skill skill2 = new Skill("java");
        Skill skill3 = new Skill("html");
        Skill skill4 = new Skill("javascript");

        this.skillRepository.persist(skill1);
        this.skillRepository.persist(skill2);
        this.skillRepository.persist(skill3);
        this.skillRepository.persist(skill4);

        Employee manager1 = new Employee("Adnan", "Khan", "RH21821", "adkhan@redhat.com", "Manager");
        SharedResource employee1 = new SharedResource("Rishi", "raj", "RISH323", "ranand@redhat.com", "engineer", "12", ResourceAvailabilityStatus.AVAILABLE);
        SharedResource employee2 = new SharedResource("Abhishek", "kumar", "ABHI323", "abkuma@redhat.com", "engineer", "23", ResourceAvailabilityStatus.UNAVAILABLE);
        SharedResource employee3 = new SharedResource("Manaswini", "das", "MDA323", "mdas@redhat.com", "engineer", "12", ResourceAvailabilityStatus.AVAILABLE);
        SharedResource employee4 = new SharedResource("Saravana", "Srinavasan", "SRA323", "mdas@redhat.com", "engineer", "12", ResourceAvailabilityStatus.AVAILABLE);

        employee1.setRoles(Set.of(role));
        employee1.setSkillSet(Set.of("java","python", "c"));
        employee2.setSkillSet(Set.of("react","javascript", "html"));
        employee3.setSkillSet(Set.of("dashbuilder"));
        employee4.setSkillSet(Set.of("react","kogito"));
        employee2.setRoles(Set.of(role2));
        manager1.setRoles(Set.of(role3));
        this.sharedResourceRepository.persist(employee1);
        this.sharedResourceRepository.persist(employee2);
        this.sharedResourceRepository.persist(employee3);
        this.sharedResourceRepository.persist(employee4);
        manager1.persist();

        ResourceRequest resourceRequest = new ResourceRequest(manager1, "Business Automation", "Kogito Website styling", "a very important one", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
        resourceRequest.setResource(employee2);
        resourceRequest.setSkillSet(Set.of("java","python", "c"));
        resourceRequest.persist();

        ResourceRequest resourceRequest2 = new ResourceRequest(manager1, "Integration", "Serverless workflow", "Documentation", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
        resourceRequest2.setResource(employee1);
        resourceRequest2.setSkillSet(Set.of("react","javascript", "html"));
        resourceRequest2.persist();

        ResourceRequest resourceRequest3 = new ResourceRequest(manager1, "Application services", "Kogito Website styling", "a very important one", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
        resourceRequest3.setResource(employee2);
        resourceRequest3.setSkillSet(Set.of("kogito","react"));
        resourceRequest3.persist();

        ResourceRequest resourceRequest4 = new ResourceRequest(manager1, "RHPAM", "Dashbuilder", "Documentation", LocalDate.now().toString(), LocalDate.now().toString(), ResourceRequestStatus.PENDING);
        resourceRequest4.setResource(employee1);
        resourceRequest4.setSkillSet(Set.of("dashbuilder"));
        resourceRequest4.persist();

        ProjectFeedback pf = new ProjectFeedback("demonstrates great responsibility", LocalDateTime.now());
        pf.persist();

        ProjectFeedback pf1 = new ProjectFeedback("needs training", LocalDateTime.now());
        pf1.persist();

        Slot slot = new Slot("2022-05-11", "2022-05-20");
        slot.persist();

        Project project = new Project("RHODS", "Managed Services", resourceRequest);
        project.setProjectManager(manager1);
        project.setSkillSet(Set.of("java","python", "c"));

//        project.setEmployee(employee1);
        project.setSlot(slot);
        project.setStatus(ProjectStatus.PENDING);
        project.persist();

        Project project1 = new Project("JBPM","Application Services", resourceRequest2);
        project1.setProjectManager(manager1);
        project1.setSkillSet(Set.of("react","javascript", "html"));
//        project1.setEmployee(employee1);
        project1.setSlot(slot);
        project1.setStatus(ProjectStatus.PENDING);
        project1.persist();

        Project project2 = new Project("Dashbuilder","Application Services", resourceRequest2);
        project2.setProjectManager(manager1);
        project2.setSkillSet(Set.of("dashbuilder"));
//        project2.setEmployee(employee1);
        project2.setSlot(slot);
        project2.setStatus(ProjectStatus.PENDING);
        project2.persist();

        Project project3 = new Project("Kogito","Application Services", resourceRequest2);
        project3.setProjectManager(manager1);
        project3.setSkillSet(Set.of("kogito","react"));
//        project3.setEmployee(employee1);
        project3.setSlot(slot);
        project3.setStatus(ProjectStatus.PENDING);
        project3.persist();

//        AccessRequest accessRequest = new AccessRequest("ranand@redhat.com", InvitationStatus.PENDING, "Temporary web site building work");
//        accessRequest.persist();


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
