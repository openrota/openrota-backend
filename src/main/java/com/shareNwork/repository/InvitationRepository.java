package com.shareNwork.repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.InvitationResponse;
import com.shareNwork.domain.QueryParams;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.domain.constants.InvitationStatus;
import com.shareNwork.proxy.MailerProxy;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@ApplicationScoped
public class InvitationRepository implements PanacheRepository<Invitation> {

    private static final Logger LOGGER = Logger.getLogger(InvitationRepository.class);
    @ConfigProperty(name = "openrota.ui.url")
    private String UI_URL;

    @Inject
    EntityManager em;

    @Inject
    SharedResourceRepository sharedResourceRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @RestClient
    MailerProxy mailerProxy;

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
    public InvitationResponse createInvitation(Invitation invitation) {
        InvitationResponse invitationResponse = new InvitationResponse();
        String token = null;

        // check if this email ID exist
        for (Invitation invitation1 : listAll()) {

                if (invitation1.getEmailId().equals(invitation.getEmailId())) {
                    invitationResponse.setResponseStatus(Response.Status.CONFLICT.getStatusCode());
                    invitationResponse.setToken(null);
                    return invitationResponse;
                }
        }

        if(isValidEmail(invitation.getEmailId())) {
            token = generateToken("email", invitation.getEmailId());
        }

        // save the invitation
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setToken(token);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setInvitationLinkParams(getInvitationParams(token, invitation.getEmailId()));
        invitation.persist();

        invitationResponse.setToken(token);
        invitationResponse.setResponseStatus(Response.Status.OK.getStatusCode());
        Role role = Role.findById(invitation.getRole().id);
        sendEmail(token, invitation.getEmailId(), role.getRoleName().name());

        return invitationResponse;
    }


    @Transactional
    public Invitation resendInvitation(Long invitationId) {
        Invitation invitation = Invitation.findById(invitationId);

        if (invitation != null) {
            final String token = generateToken("email", invitation.getEmailId());

            // save the invitation and send email
            invitation.setCreatedAt(LocalDateTime.now());
            invitation.setToken(token);
            invitation.setStatus(InvitationStatus.PENDING);
            invitation.setInvitationLinkParams(getInvitationParams(token, invitation.getEmailId()));
            sendEmail(token, invitation.getEmailId(), invitation.getRole().getRoleName().name());
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

    private void sendEmail(String token, String email, String role){
        final EmailData emailData = EmailData.builder()
                .emailType(EmailType.OPENROTA_INVITATION.value())
                .mailTo(email)
                .emailTemplateVariables(Map.of("invitationLink", getInvitationParams(token, email),
                                               "role", role))
                .build();
        Response response = mailerProxy.sendEmail(emailData);
        LOGGER.info(response.getStatus());
    }

    private String getInvitationParams(final String token, final String email) {
        String queryParams = new QueryParams()
                .addParam("token", token)
                .addParam("emailId", email)
                .asString();
        return UI_URL + "?" + queryParams;
    }

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
                        employeeRepository.createEmployeeWithRole(emailId, name, Set.of(invitation1.getRole()));
//                        sharedResourceRepository.createSharedResourceAccount(name, emailId);
                        em.merge(invitation1);
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

    @Transactional
    public List<Invitation> expiringInvitations(final int days) {
        return streamAll()
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.PENDING)
                        && Duration.between(invitation.getCreatedAt(), LocalDateTime.now()).toDays() <= days)
                .collect(Collectors.toList());
    }

}
