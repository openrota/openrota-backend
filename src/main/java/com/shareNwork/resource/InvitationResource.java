package com.shareNwork.resource;

import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class InvitationResource {

    private InvitationRepository invitationRepository;

    @Query("invitation")
    @Description("Get all invitations")
    public List<Invitation> findAllInvitations() {
        return this.invitationRepository.findAll().list();
    }

    @Mutation
    @Description("Create a new token")
    public List<InvitationResponse> createInvitationToken(List<Invitation> invitationlist) {
        return this.invitationRepository.createInvitationToken(invitationlist);
    }

    @Query("verify")
    @Description("Verify Email")
    public boolean verifyEmail(String emailId, String token) {
        return this.invitationRepository.verifyToken(emailId, token);
    }
}
