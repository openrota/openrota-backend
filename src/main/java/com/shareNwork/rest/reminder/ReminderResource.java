package com.shareNwork.rest.reminder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.shareNwork.service.ReminderService;

@Path("/reminder")
public class ReminderResource {

    @Inject
    ReminderService reminderService;

    @POST
    public Response triggerReminders() {
        reminderService.callReminders();
        return Response.ok().build();
    }
}
