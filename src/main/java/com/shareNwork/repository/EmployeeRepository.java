package com.shareNwork.repository;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.Role;
import com.shareNwork.domain.SharedResource;
import com.shareNwork.domain.filters.EmployeeFilter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    @Inject
    EntityManager em;

    @Transactional
    public Employee create(Employee employee) throws ParseException {
        em.persist(employee);
        return employee;
    }

    @Transactional
    public Employee findByEmailId(String emailId) {
        return find("emailId", emailId).firstResult();
    }

    @Transactional
    public List<Employee> findByCriteria(EmployeeFilter filter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        Predicate predicate = null;
        if (filter.getFirstName() != null)
            predicate = filter.getFirstName().generateCriteria(builder, root.get("firstName"));
        if (filter.getLastName() != null)
            predicate = (predicate == null ?
                    filter.getLastName().generateCriteria(builder, root.get("lastName")) :
                    builder.and(predicate, filter.getLastName().generateCriteria(builder, root.get("lastName"))));
        if (filter.getEmailId() != null)
            predicate = (predicate == null ? filter.getEmailId().generateCriteria(builder, root.get("emailId")) :
                    builder.and(predicate, filter.getEmailId().generateCriteria(builder, root.get("emailId"))));

        if (predicate != null)
            criteriaQuery.where(predicate);

        return em.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public void createEmployeeWithRole(String email, String name, Set<Role> role) {
        Employee employee = new Employee();
        employee.setFirstName(name);
        employee.setEmailId(email);
        employee.setRoles(role);
        employee.persist();
    }


}
