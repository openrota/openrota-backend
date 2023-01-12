package com.shareNwork.resource;

import com.shareNwork.domain.AllowedDesignationResponse;
import com.shareNwork.repository.AllowedDesignationRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

@AllArgsConstructor
@GraphQLApi
public class AllowedDesignationResource {

    private AllowedDesignationRepository allowedDesignationRepository;

    @Mutation
    @Description("Check whether designation is whitelisted")
    public AllowedDesignationResponse verifyDesignation(String designation) {
        return this.allowedDesignationRepository.verify(designation);
    }
}
