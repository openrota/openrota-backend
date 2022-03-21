package com.shareNwork.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.domain.constants.InvitationStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.Location;
import org.apache.commons.lang3.time.DateUtils;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@ApplicationScoped

public class InvitationRepository implements PanacheRepository<Invitation> {

    @Inject
    EntityManager em;

    @Inject
    SharedResourceRepository sharedResourceRepository;

    @Inject
    @Location("sharedResourceInvitation")
    MailTemplate sharedResourceInvitation;

    public static String decodeToken(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = null;
        String[] chunks = token.split("\\.");
        if (chunks.length > 1) {
            payload = new String(decoder.decode(chunks[1]));
        }
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
    public InvitationResponse createInvitation(Invitation invitation) {
        InvitationResponse invitationResponse = new InvitationResponse();

        // check if this email ID exist
        for (Invitation invitation1 : listAll()) {
            if (invitation1.getEmailId().equals(invitation.getEmailId())) {
                invitationResponse.setResponseStatus(Response.Status.CONFLICT.getStatusCode());
                invitationResponse.setToken(null);
                return invitationResponse;
            }
        }

        final String token = generateToken("email", invitation.getEmailId());

        // save the invitation
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setToken(token);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.persist();

        invitationResponse.setToken(generateToken("email", invitation.getEmailId()));
        invitationResponse.setResponseStatus(Response.Status.OK.getStatusCode());

        return invitationResponse;
    }


    @Transactional
    public Invitation resendInvitation(Long invitationId) {
        Invitation invitation = Invitation.findById(invitationId);

        if (invitation != null) {
            final String token = generateToken("email", invitation.getEmailId());

            // save the invitation
            invitation.setCreatedAt(LocalDateTime.now());
            invitation.setToken(token);
            invitation.setStatus(InvitationStatus.PENDING);
            invitation.persist();
        } else {
            throw new WebApplicationException("Invitation not found");
        }
        return invitation;
    }

    private String generateToken(String claim, String str) {
        return Jwts.builder()
                .claim(claim, str)
                .setIssuedAt(new java.util.Date())
                .setExpiration(DateUtils.addHours(new java.util.Date(), 3))
                .signWith(
                        HS256,
                        TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
                )
                .compact();
    }

    @Transactional
    public InvitationResponse verifyToken(String emailId, String token, String name) {

        List<Invitation> invitations = listAll();

        for (Invitation invitation1 : invitations) {
            if (invitation1.getEmailId().equals(emailId)) {
                if (invitation1.getStatus().equals(InvitationStatus.PENDING)) {
                    String dataFromToken = decodeToken(token);
                    String extractedEmailFromToken = (dataFromToken != null ? dataFromToken.substring(dataFromToken.indexOf(":") + 2, dataFromToken.indexOf(",") - 1) : "");
                    if (extractedEmailFromToken.equals(emailId)) {
                        invitation1.setStatus(InvitationStatus.COMPLETED);
                        em.merge(invitation1);
                        sharedResourceRepository.createSharedResourceAccount(name, emailId);
                        return (new InvitationResponse(Response.Status.OK.getStatusCode(), "Token verified succesfully, please complete your profile details"));
                    }
                    return (new InvitationResponse(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid token, please ask your system admin to generate a new token for " + emailId));
                } else if (invitation1.getStatus().equals(InvitationStatus.COMPLETED)) {
                    return (new InvitationResponse(Response.Status.FOUND.getStatusCode(), "User already verified, If still unable to access the system, please contact system admin"));
                }
            }
        }
        return (new InvitationResponse(Response.Status.NOT_FOUND.getStatusCode(), "No invitations found for email " + emailId));
    }
}
