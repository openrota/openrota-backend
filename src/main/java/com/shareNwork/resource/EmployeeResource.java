package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.filters.EmployeeFilter;
import com.shareNwork.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.*;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class EmployeeResource {
    private EmployeeRepository employeeRepository;

    @Query("employee")
    @Description("Get all Employees")
    public List<Employee> findAll() {

        return this.employeeRepository.findAll().list();
    }

    @Query("employeesWithFilter")
    @Description("Get all Employees using the filters eq, lt,le,gt,ge")
    public List<Employee> findWithFilter(@Name("filter") EmployeeFilter filter) {
        return this.employeeRepository.findByCriteria(filter);
    }

    @Query("employeeById")
    @Description("Get an employee by id")
    public Employee findById(@Name("id") Long id) {
        return this.employeeRepository.findById(id);
    }

    @Mutation
    @Description("Create a new Employee")
    public Employee createEmployee(Employee employee) throws ParseException {
        return this.employeeRepository.create(employee);
    }

}
