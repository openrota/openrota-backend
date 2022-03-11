package com.shareNwork.resource;

import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.repository.InvitationRepository;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

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

    @Query("invitationsByPage")
    @Description("Get Invitations with pagination")
    public List<Invitation> getInvitationsByPage(@Name("pageSize") int pageSize, @Name("pageOffset") int pageOffset) {
        return this.invitationRepository.findAll().page(Page.of(pageOffset, pageSize)).list();
    }

    @Mutation
    @Description("Create a new token")
    public InvitationResponse createInvitationToken(Invitation invitation) {
        return this.invitationRepository.createInvitation(invitation);
    }

    @Mutation
    @Description("Verify Invitation")
    public InvitationResponse verifyInvitation(String emailId, String token, String name) {
        return this.invitationRepository.verifyToken(emailId, token, name);
    }
}
