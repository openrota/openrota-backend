package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
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

    @Mutation
    @Description("Create a new Employee")
    public Employee createEmployee(Employee employee) throws ParseException {
        return this.employeeRepository.createEmployee(employee);
    }

}
