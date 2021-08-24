package com.shareNwork.resource;

import com.shareNwork.domain.Invitation;
import com.shareNwork.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.text.ParseException;

@AllArgsConstructor
@GraphQLApi
public class InvitationResource {

   private InvitationRepository invitationRepository;

   @Mutation
   @Description("Create a new token")
   public String createInvitationToken(Invitation invitation) throws ParseException {
      return this.invitationRepository.createInvitationToken(invitation);
   }

   @Query("token")
   @Description("Get Token")
   public String getInvitationToken(Invitation invitation) {
      return this.invitationRepository.createInvitationToken(invitation);
   }
}
