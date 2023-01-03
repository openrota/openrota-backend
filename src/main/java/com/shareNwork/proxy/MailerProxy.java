package com.shareNwork.proxy;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/mailer")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:8082")
public interface MailerProxy {

    @POST
    @Path("/mail")
    Response sendEmail(EmailData emailData);

    @POST
    @Path("/multi-mail")
    Response sendMultipleEmail(List<EmailData> emailDataList);
}
