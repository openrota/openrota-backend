package com.shareNwork.resource;

import java.util.List;

import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class InvitationResource {

    private InvitationRepository invitationRepository;

    @Query("invitation")
    @Description("Get all invitations")
    public List<Invitation> findAllInvitations() {
        return this.invitationRepository.listAll();
    }

    @Query("getInvitationById")
    @Description("Get invitations by Id")
    public Invitation getInvitationById(long id) {
        return this.invitationRepository.findById(id);
    }

    @Mutation
    @Description("Create a new token")
    public InvitationResponse createInvitationToken(Invitation invitation) {
        return this.invitationRepository.createInvitation(invitation);
    }

    @Mutation
    @Description("Refresh token")
    public Invitation resendInvitation(Long id) {
        return this.invitationRepository.resendInvitation(id);
    }

    @Mutation
    @Description("Verify Invitation")
    public InvitationResponse verifyInvitation(String emailId, String token, String name) {
        return this.invitationRepository.verifyToken(emailId, token, name);
    }
}
