package com.shareNwork.rest.roaster;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/rest/roster")
@ApplicationScoped
public class RoasterController {
    private final RoasterService rosterService;

    @Inject
    public RoasterController(RoasterService rosterService) {
        this.rosterService = rosterService;
    }

    @POST
    @Path("/solve")
    public void solveRoster() {
        rosterService.solveRoster(12);
    }

}
