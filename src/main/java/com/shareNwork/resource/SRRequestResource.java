package com.shareNwork.resource;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.constants.RowAction;
import com.shareNwork.repository.ResourceRequestRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class SRRequestResource {

    private ResourceRequestRepository resourceRequestRepository;

    @Query("sharedResourceRequest")
    @Description("Get all resources request")
    @Transactional
    public List<ResourceRequest> getAllSharedResourceRequest() {
        return resourceRequestRepository.listAll();
    }

    @Query("sharedResourceRequestByRequestorId")
    @Description("Get all resources request by requestor")
    @Transactional
    public List<ResourceRequest> getAllSharedResourceRequestbyRequestor(long id) {
        return resourceRequestRepository.getSharedResourceByRequestor(id);
    }

    @Query("sharedResourceRequestById")
    @Description("Get resources request by id")
    @Transactional
    public ResourceRequest sharedResourceRequestById(long id) {
        return ResourceRequest.findById(id);
    }

    @Mutation
    @Description("Create a new resource request")
    public com.shareNwork.domain.ResourceRequest createOrUpdateResourceRequest(com.shareNwork.domain.ResourceRequest resourceRequest) throws ParseException {
        return this.resourceRequestRepository.updateOrCreate(resourceRequest);
    }

    @Mutation
    @Description("resource request actions")
    public com.shareNwork.domain.ResourceRequest handleResourceRequestActions(RowAction action, ResourceRequest resourceRequest) throws ParseException {
        return this.resourceRequestRepository.handleActions(action, resourceRequest);
    }
}
