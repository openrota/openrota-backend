package com.shareNwork.resource;

import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.ResourceRequestSkillsProficiency;
import com.shareNwork.repository.ResourceRequestRepository;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

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

    @Query("sharedResourceRequestsByPage")
    @Description("Get SharedResourceRequest with pagination")
    public List<ResourceRequest> getAccessRequestsByPage(@Name("pageSize") int pageSize, @Name("pageOffset") int pageOffset) {
        return this.resourceRequestRepository.findAll().page(Page.of(pageOffset, pageSize)).list();
    }

    @Query("getSkillsByRequestId")
    @Description("Get required skills of request Id")
    @Transactional
    public List<ResourceRequestSkillsProficiency> getSkillsByRequestId(long id) {
        return resourceRequestRepository.getSkillsByRequestId(id);
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

}
