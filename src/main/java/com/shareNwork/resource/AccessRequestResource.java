package com.shareNwork.resource;

import java.util.List;

import javax.transaction.Transactional;

import com.shareNwork.domain.AccessRequest;
import com.shareNwork.domain.constants.InvitationStatus;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class AccessRequestResource {

    @Query("accessRequest")
    @Description("Get all access requests")
    public List<AccessRequest> findAllRequests() {
        return AccessRequest.listAll();
    }

    @Query("accessRequestsByPage")
    @Description("Get AccessRequests with pagination")
    public List<AccessRequest> getAccessRequestsByPage(@Name("pageSize") int pageSize, @Name("pageOffset") int pageOffset) {
        return AccessRequest.findAll().page(Page.of(pageOffset, pageSize)).list();
    }

    @Mutation
    @Description("Create a new access request")
    @Transactional
    public AccessRequest createAccessRequest(AccessRequest accessRequest) {
         if (accessRequest.id == null) {
             accessRequest.setStatus(InvitationStatus.PENDING);
             accessRequest.persist();
             return accessRequest;
         }
        return null;
    }

}
