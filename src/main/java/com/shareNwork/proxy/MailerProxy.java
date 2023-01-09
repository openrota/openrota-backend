package com.shareNwork.proxy;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.logging.Logger;

@Path("/mailer")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface MailerProxy {

    static final Logger LOGGER = Logger.getLogger(MailerProxy.class);

    @POST
    @Path("/mail")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(MailerResponseFallback.class)
    Response sendEmail(EmailData emailData);

    @POST
    @Path("/multi-mail")
    @Retry(maxRetries = 2, delay = 2000)
    @Fallback(MailerResponseFallback.class)
    Response sendMultipleEmail(List<EmailData> emailDataList);

    public static class MailerResponseFallback implements FallbackHandler<Response> {

        private static final Response EMPTY_RESPONSE = Response.serverError().build();

        @Override
        public Response handle(ExecutionContext context) {
            LOGGER.error("Openrota Mailer Service is Unavailable!");
            return EMPTY_RESPONSE;
        }
    }
}
