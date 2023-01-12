package com.shareNwork.resource;

import java.text.ParseException;
import java.util.List;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.filters.EmployeeFilter;
import com.shareNwork.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class EmployeeResource {

    private EmployeeRepository employeeRepository;

    @Query("employee")
    @Description("Get all Employees")
    public List<Employee> findAll() {
        return this.employeeRepository.findAll().list();
    }

    @Query("employeeByEmailId")
    @Description("Get an employee by emailId")
    public com.shareNwork.domain.Employee getEmployeeByEmailId(@Name("emailId") String emailId) {
        return this.employeeRepository.findByEmailId(emailId);
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
