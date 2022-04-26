package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.Role;
import com.shareNwork.domain.Skill;
import com.shareNwork.repository.EmployeeRepository;
import com.shareNwork.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@GraphQLApi
public class RoleResource {

    private EmployeeRepository employeeRepository;

    @Query("roles")
    @Description("Get all roles")
    public List<Role> findAll() {
        return Role.findAll().list();
    }

    @Query("getRoleByEmployeeId")
    @Description("Get roles by employee")
    public Set<Role> getRoleByEmployeeId(Long employeeId) throws ParseException {
        Employee employee = Employee.findById(employeeId);
        if (employee != null) {
            return employee.getRoles();
        }
        return null;
    }

}

