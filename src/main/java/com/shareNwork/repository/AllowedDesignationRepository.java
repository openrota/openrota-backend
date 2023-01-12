package com.shareNwork.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.shareNwork.domain.AllowedDesignation;
import com.shareNwork.domain.AllowedDesignationResponse;
import com.shareNwork.domain.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AllowedDesignationRepository implements PanacheRepository<Employee> {

    @Inject
    EntityManager em;

    @Transactional
    public AllowedDesignationResponse verify(String designation) {
        List<AllowedDesignation> allowedDesignationList = AllowedDesignation.listAll();
        AllowedDesignationResponse allowedDesignationResponse = new AllowedDesignationResponse();
        allowedDesignationResponse.setDesignationName(designation);
        for (AllowedDesignation allowedDesignation : allowedDesignationList) {
            if (allowedDesignation.getName().equals(designation)) {
                allowedDesignationResponse.setIsgranted(true);

                return allowedDesignationResponse;
            }
        }
        allowedDesignationResponse.setIsgranted(false);
        return allowedDesignationResponse;
    }
}
