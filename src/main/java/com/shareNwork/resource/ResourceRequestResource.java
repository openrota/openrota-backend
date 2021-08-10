package com.shareNwork.resource;

import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.repository.ResourceRequestRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class ResourceRequestResource {
    private ResourceRequestRepository resourceRequestRepository;

    @Mutation
    @Description("Create a new ResourceRequest")
    public ResourceRequest createOrUpdateResourceRequest(ResourceRequest resourceRequest) throws ParseException {
        return this.resourceRequestRepository.createOrUpdateResource(resourceRequest);
    }

    @Query
    @Description("get all ResourceRequest")
    public List<ResourceRequest> getAllResourceRequest() {
        return this.resourceRequestRepository.findAll().list();
    }

    @Mutation
    @Description("Delete ResourceRequest")
    public ResourceRequest deleteResourceRequest(Long id) throws ParseException {
        return this.resourceRequestRepository.deleteResourceRequest(id);
    }
}

