package com.shareNwork.resource;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.filters.EmployeeFilter;
import com.shareNwork.repository.SharedResourceRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.*;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class SharedResource {

    private SharedResourceRepository sharedResourceRepository;

    @Query("sharedResource")
    @Description("Get all resources")
    public List<com.shareNwork.domain.SharedResource> getAllSharedResource() {
        return this.sharedResourceRepository.getAllSRs();
    }

    @Query("sharedResourceById")
    @Description("Get an SR by id")
    public com.shareNwork.domain.SharedResource getSRById(@Name("id") Long id) {
        return this.sharedResourceRepository.findById(id);
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
    @Description("Delete SR")
    public com.shareNwork.domain.SharedResource deleteSharedResource(Long id) throws ParseException {
        return this.sharedResourceRepository.deleteSharedResource(id);
    }

}
