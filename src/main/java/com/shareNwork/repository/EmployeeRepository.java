package com.shareNwork.repository;

import com.shareNwork.domain.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    @Transactional
    public Employee createEmployee(Employee employee) throws ParseException {
        this.persist(employee);
        return employee;
    }


}
