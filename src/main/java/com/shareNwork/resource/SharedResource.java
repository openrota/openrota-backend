package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.EmployeeSkillProficiency;
import com.shareNwork.domain.filters.EmployeeFilter;
import com.shareNwork.repository.SharedResourceRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.*;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class SharedResource {

    private SharedResourceRepository sharedResourceRepository;

    @Query("sharedResource")
    @Description("Get all resources")
    @Transactional
    public List<com.shareNwork.domain.SharedResource> getAllSharedResource() {
        return this.sharedResourceRepository.listAll();
    }

    @Query("sharedResourceById")
    @Description("Get an SR by id")
    public com.shareNwork.domain.SharedResource getSRById(@Name("id") Long id) {
        return this.sharedResourceRepository.findById(id);
    }

    @Query("sharedResourceByEmailId")
    @Description("Get an SR by emailId")
    public com.shareNwork.domain.SharedResource getSRByEmailId(@Name("emailId") String emailId) {
        return this.sharedResourceRepository.findByEmailId(emailId);
    }

    @Query("sharedResourceWithFilters")
    @Description("Get all resources using the filters eq, lt,le,gt,ge")
    public List<Employee> findWithFilter(@Name("filter") EmployeeFilter filter) {
        return this.sharedResourceRepository.findByCriteria(filter);
    }

    @Mutation
    @Description("Create a new SR")
    public com.shareNwork.domain.SharedResource createOrUpdateSharedResource(com.shareNwork.domain.SharedResource resource) throws ParseException {
        return this.sharedResourceRepository.updateOrCreate(resource);
    }

    @Mutation
    @Description("Add skills to SR")
    public com.shareNwork.domain.SharedResource addSkillsToSR(Long id, List<EmployeeSkillProficiency> employeeSkillProficiencies) throws ParseException {
        return this.sharedResourceRepository.addSkillsToEmployee(id, employeeSkillProficiencies);
    }

    @Mutation
    @Description("Update skill of SR")
    public EmployeeSkillProficiency updateSkillOfSR(Long id, EmployeeSkillProficiency employeeSkillProficiency) throws ParseException {
        return this.sharedResourceRepository.updateSkillsOfEmployee(id, employeeSkillProficiency);
    }

    @Mutation
    @Description("Delete skills of SR")
    public EmployeeSkillProficiency deleteSkillForSR(Long id, EmployeeSkillProficiency employeeSkillProficiency) throws ParseException {
        return this.sharedResourceRepository.deleteSkillsOfEmployee(id, employeeSkillProficiency);
    }

    @Mutation
    @Description("Delete SR")
    public com.shareNwork.domain.SharedResource deleteSharedResource(Long id) throws ParseException {
        return this.sharedResourceRepository.deleteSharedResource(id);
    }

}
