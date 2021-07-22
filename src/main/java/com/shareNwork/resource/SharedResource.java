package com.shareNwork.resource;

import com.shareNwork.domain.Project;
import com.shareNwork.repository.ProjectRepository;
import com.shareNwork.repository.SharedResourceRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class SharedResource {

    private SharedResourceRepository sharedResourceRepository;

    @Query("sharedResource")
    @Description("Get all resources")
    public List<com.shareNwork.domain.SharedResource> findAll() {
        return this.sharedResourceRepository.findAll().list();
    }

    @Mutation
    @Description("Create a new Employee")
    public com.shareNwork.domain.SharedResource createSharedResource(com.shareNwork.domain.SharedResource resource) throws ParseException {
        return this.sharedResourceRepository.createEmployee(resource);
    }

}
