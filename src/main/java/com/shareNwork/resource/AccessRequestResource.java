package com.shareNwork.resource;

import com.shareNwork.domain.AccessRequest;
import com.shareNwork.domain.AllowedDesignationResponse;
import com.shareNwork.domain.EmailData;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.domain.constants.InvitationStatus;
import com.shareNwork.domain.constants.RowAction;

import com.shareNwork.repository.AccessRequestRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class AccessRequestResource {
    private AccessRequestRepository accessRequestRepository;

    @Query("accessRequest")
    @Description("Get all access requests")
    public List<AccessRequest> findAllRequests() {
        return AccessRequest.listAll();
    }

    @Query("accessRequestbyId")
    @Description("Get access request by Id")
    public AccessRequest findAllRequests(Long id) {
        return accessRequestRepository.findById(id);
    }

    @Query("isResourceAccessAllowed")
    @Description("isResourceAccessAllowed")
    public AllowedDesignationResponse isResourceAccessAllowed(String email) {
        List<AccessRequest> accessRequests = AccessRequest.listAll();
        AllowedDesignationResponse allowedDesignationResponse = new AllowedDesignationResponse();
        allowedDesignationResponse.setDesignationName(email);
        for (AccessRequest accessRequest: accessRequests) {
        if (accessRequest.getEmailId().equals(email) && accessRequest. getStatus().equals(InvitationStatus.COMPLETED) ) {
            allowedDesignationResponse.setIsgranted(true);
            return allowedDesignationResponse ;
        }
        allowedDesignationResponse.setIsgranted(false);
        }
        return allowedDesignationResponse;
    }

    @Mutation
    @Description("Create a new access request")
    @Transactional
    public AccessRequest createAccessRequest(AccessRequest accessRequest) {
         if (accessRequest.id == null) {
             accessRequest.setStatus(InvitationStatus.PENDING);
             accessRequest.persist();
             accessRequestRepository.sendEmail(EmailData.builder()
                                           .emailType(EmailType.NEW_ACCESS_REQ.value())
                                           .mailTo(accessRequest.getEmailId())
                                           .emailTemplateVariables(Collections.emptyMap())
                                           .build());
             return accessRequest;
         }
        return null;
    }

    @Mutation
    @Description("handle access request actions")
    public com.shareNwork.domain.AccessRequest handleAccessRequestActions(RowAction actionName, AccessRequest accessRequest) throws ParseException {
        return this.accessRequestRepository.handleActions(actionName, accessRequest);
    }

}
