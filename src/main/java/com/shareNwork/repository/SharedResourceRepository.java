package com.shareNwork.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.EmployeeSkillProficiency;
import com.shareNwork.domain.SharedResource;
import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import com.shareNwork.domain.constants.RoleType;
import com.shareNwork.domain.filters.EmployeeFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SharedResourceRepository implements PanacheRepository<SharedResource> {

    @Inject
    EntityManager em;

    @Inject
    RoleRepository roleRepository;

    @Transactional
    public SharedResource updateOrCreate(SharedResource shareResource) throws ParseException {
        if (shareResource.id == null) {
            persist(shareResource);
//            if(shareResource.getSkillProficiencies() != null) {
//                addSkillsToEmployee(shareResource.id, shareResource.getSkillProficiencies());
//            }
            return shareResource;
        } else {
//            addSkillsToEmployee(shareResource.id, shareResource.getSkillProficiencies());
            return em.merge(shareResource);
        }
    }

    @Transactional
    public SharedResource createSharedResourceAccount(String name, String emailId) {
        SharedResource sharedResource = new SharedResource();
        sharedResource.setFirstName(name);
        sharedResource.setEmailId(emailId);
        sharedResource.setStatus(ResourceAvailabilityStatus.AVAILABLE);
        sharedResource.setDesignation(" Associate software Engineer");
        sharedResource.setRoles(roleRepository.getRolesbyRoleTypes(Set.of(RoleType.RESOURCE)));
        sharedResource.persist();
        return sharedResource;
    }

    @Transactional
    public SharedResource addSkillsToEmployee(Long id, List<EmployeeSkillProficiency> employeeSkillProficiencies) throws ParseException {
        SharedResource employee = findById(id);
        if (employee == null) {
            throw new NotFoundException();
        } else {
            for (EmployeeSkillProficiency employeeSkillProficiency : employeeSkillProficiencies) {
                if (employeeSkillProficiency.id != null) {
                    updateSkillsOfEmployee(employeeSkillProficiency.id, employeeSkillProficiency);
                } else {
                    employeeSkillProficiency.setEmployee(employee);
                    employeeSkillProficiency.persist();
                }
            }
        }
        return employee;
    }

    @Transactional
    public EmployeeSkillProficiency updateSkillsOfEmployee(Long id, EmployeeSkillProficiency employeeSkillProficiency) throws ParseException {
        EmployeeSkillProficiency employeeSkillProficiency1 = EmployeeSkillProficiency.findById(employeeSkillProficiency.id);
        if (employeeSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        if (employeeSkillProficiency.getSkill() != null) {
            employeeSkillProficiency1.setSkill(employeeSkillProficiency.getSkill());
        }
        if (employeeSkillProficiency.getProficiencyLevel() != null) {
            employeeSkillProficiency1.setProficiencyLevel(employeeSkillProficiency.getProficiencyLevel());
        }
        employeeSkillProficiency1.persist();
        return employeeSkillProficiency1;
    }

    @Transactional
    public EmployeeSkillProficiency deleteSkillsOfEmployee(Long id, EmployeeSkillProficiency employeeSkillProficiency) throws ParseException {
        EmployeeSkillProficiency employeeSkillProficiency1 = EmployeeSkillProficiency.findById(employeeSkillProficiency.id);
        if (employeeSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        employeeSkillProficiency1.delete();
        return employeeSkillProficiency1;
    }

    @Transactional
    public SharedResource deleteSharedResource(Long id) throws ParseException {
        SharedResource p = findById(id);
        if (p != null) {
            deleteById(id);
        }
        return p;
    }

    @Transactional
    public SharedResource findByEmailId(String emailId) {
        return find("emailId", emailId).firstResult();
    }

    @Transactional
    public List<Employee> findByCriteria(EmployeeFilter filter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        Predicate predicate = null;
        if (filter.getFirstName() != null) {
            predicate = filter.getFirstName().generateCriteria(builder, root.get("firstName"));
        }
        if (filter.getLastName() != null) {
            predicate = (predicate == null ?
                    filter.getLastName().generateCriteria(builder, root.get("lastName")) :
                    builder.and(predicate, filter.getLastName().generateCriteria(builder, root.get("lastName"))));
        }
        if (filter.getEmailId() != null) {
            predicate = (predicate == null ? filter.getEmailId().generateCriteria(builder, root.get("emailId")) :
                    builder.and(predicate, filter.getEmailId().generateCriteria(builder, root.get("emailId"))));
        }

        if (predicate != null) {
            criteriaQuery.where(predicate);
        }

        return em.createQuery(criteriaQuery).getResultList();
    }
}
