package com.shareNwork.repository;

import com.shareNwork.domain.*;
import com.shareNwork.domain.constants.SkillProficiencyLevel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class SharedResourceRepository implements PanacheRepository<SharedResource> {
    private SharedResourceRepository employeeRepository;
    private SkillRepository skillRepository;
    @Transactional
    public SharedResource createEmployee(SharedResource employee) throws ParseException {
        this.persist(employee);
        return employee;
    }
}
