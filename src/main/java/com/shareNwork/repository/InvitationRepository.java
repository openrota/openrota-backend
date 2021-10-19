package com.shareNwork.repository;

import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.domain.constants.InvitationStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;

import org.apache.commons.lang3.time.DateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@ApplicationScoped

public class InvitationRepository implements PanacheRepository<Invitation> {

    @Inject
    EntityManager em;

    @Inject
    @Location("sharedResourceInvitation")
    MailTemplate sharedResourceInvitation;

    public static String decodeToken(String token) {
        Base64.Decoder decoder = Base64.getDecoder();

        String[] chunks = token.split("\\.");

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return payload;
    }

    @Transactional
    public List<InvitationResponse> createInvitationToken(List<Invitation> invitationlist) {
        List<InvitationResponse> responses = new ArrayList<>();
        for (Invitation invitation : invitationlist) {

            InvitationResponse invitationResponse = new InvitationResponse();
            List<Invitation> invitations = listAll();

            for (Invitation invitation1 : invitations) {
                if (invitation1.getEmailId().equals(invitation.getEmailId())) {
                    invitationResponse.setResponseStatus(Response.Status.CONFLICT.getStatusCode());
                    invitationResponse.setToken(null);
                    responses.add(invitationResponse);
                    return responses;
                }
            }

            String token = Jwts.builder()
                    .claim("email", invitation.getEmailId())
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(DateUtils.addHours(new java.util.Date(), 3))
                    .signWith(
                            HS256,
                            TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
                    )
                    .compact();

            invitation.setStatus(InvitationStatus.PENDING);
            persist(invitation);
            invitationResponse.setToken(token);
            invitationResponse.setResponseStatus(Response.Status.OK.getStatusCode());
            String inviteURL = "https://prod.foo.redhat.com:1337/?token=" + token + "&email=" + invitation.getEmailId();
            sharedResourceInvitation.to(invitation.getEmailId())
                    .subject("[Action Required] Invitation from OpenRota")
                    .data("invitationLink", inviteURL)
                    .send().subscribe().with(t -> System.out.println("Mail sent to " + invitation.getEmailId()));
            responses.add(invitationResponse);
        }
        return responses;
    }

    @Transactional
    public boolean verifyToken(String emailId, String token) {

        List<Invitation> invitations = listAll();

        for (Invitation invitation1 : invitations) {

            if (invitation1.getEmailId().equals(emailId)) {
                if (invitation1.getStatus().equals(InvitationStatus.PENDING)) {

                    String dataFromToken = decodeToken(token);
                    String extractedEmailFromToken = dataFromToken.substring(dataFromToken.indexOf(":") + 2, dataFromToken.indexOf(",") - 1);
                    invitation1.setStatus(InvitationStatus.COMPLETED);
                    em.merge(invitation1);
                    return extractedEmailFromToken.equals(emailId);
                }
                else if (invitation1.getStatus().equals(InvitationStatus.COMPLETED)) {
                    return true;
                }
            }
        }
        return false;
    }

}
