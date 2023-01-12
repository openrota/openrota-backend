package com.shareNwork.rest.roaster;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.shareNwork.domain.Roaster;

@Path("/rest/roster")
@ApplicationScoped
public class RoasterController {

    private final RoasterService rosterService;

    @Inject
    public RoasterController(RoasterService rosterService) {
        this.rosterService = rosterService;
    }

    @GET
    public Roaster getRoster() {
        return rosterService.getRoster();
    }

    @GET
    @Path("/solve")
    public void solveRoster() {
        rosterService.solveRoster(12);
    }
}
