package com.shareNwork.repository;

import com.shareNwork.domain.EmployeeSkillProficiency;
import com.shareNwork.domain.SharedResource;
import com.shareNwork.domain.Skill;
import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.runtime.StartupEvent;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@NoArgsConstructor
@ApplicationScoped
public class BootstrapRepo {

    private SkillRepository skillRepository;
    private SharedResourceRepository sharedResourceRepository;

    @Inject
    public BootstrapRepo(SkillRepository skillRepository, SharedResourceRepository sharedResourceRepository) {
        this.skillRepository = skillRepository;
        this.sharedResourceRepository = sharedResourceRepository;
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

        SharedResource employee1 = new SharedResource("Rishi", "raj", "RISH323", "ranand@redhat.com", "engineer", "12");
        SharedResource employee2 = new SharedResource("Abhishek", "kumar", "ABHI323", "ankumr@redhat.com", "engineer", "23");

        this.sharedResourceRepository.persist(employee1);
        this.sharedResourceRepository.persist(employee2);

        EmployeeSkillProficiency employeeSkillProficiency = new EmployeeSkillProficiency(SkillProficiencyLevel.ADVANCED);
        employeeSkillProficiency.setSkill(skill1);
        employeeSkillProficiency.setEmployee(employee1);
        employeeSkillProficiency.persist();

        EmployeeSkillProficiency employeeSkillProficiency2 = new EmployeeSkillProficiency(SkillProficiencyLevel.BEGINNER);
        employeeSkillProficiency2.setSkill(skill2);
        employeeSkillProficiency2.setEmployee(employee2);
        employeeSkillProficiency2.persist();
    }

}
