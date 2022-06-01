package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.Role;
import com.shareNwork.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@GraphQLApi
public class RoleResource {

    private EmployeeRepository employeeRepository;

    @Inject
    EntityManager em;

    @Query("roles")
    @Description("Get all roles")
    public List<Role> findAll() {
        return Role.findAll().list();
    }

    @Query("roleByEmployeeId")
    @Description("Get roles by employee")
    public Set<Role> getRoleByEmployeeId(Long employeeId) throws ParseException {
        Employee employee = Employee.findById(employeeId);
        if (employee != null) {
            return employee.getRoles();
        }
        return null;
    }
    @Mutation
    @Description("Add roles to Employee")
    @Transactional
    public com.shareNwork.domain.Employee addRoleToEmployee(Long employeeId, Set<Role> roles) throws ParseException {
        Employee employee = Employee.findById(employeeId);
        if (employee != null) {
             employee.setRoles(roles);
            em.merge(employee);
             return employee;
        }
        throw new WebApplicationException("Employee was not found");
    }
}

