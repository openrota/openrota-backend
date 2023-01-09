package com.shareNwork.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import com.shareNwork.domain.Invitation;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.proxy.MailerProxy;
import com.shareNwork.repository.InvitationRepository;
import com.shareNwork.repository.ProjectRepository;
import com.shareNwork.service.ReminderSchedulers;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ReminderSchedulersImpl implements ReminderSchedulers {

    private static final Logger LOGGER = Logger.getLogger(ReminderSchedulersImpl.class);
    public static final String CRON_TIME = "* 15 10 * * ?";

    @Inject
    InvitationRepository invitationRepository;

    @Inject
    ProjectRepository projectRepository;

    @RestClient
    MailerProxy mailerProxy;

    @Override
    @Scheduled(cron = CRON_TIME)
    public void invitationExpireReminder() {
        List<Invitation> emailIds = invitationRepository.expiringInvitations(3);
        List<EmailData> emailDataList = toEmailData(emailIds, 3);
        LOGGER.info(emailDataList.toString());
        if (!emailDataList.isEmpty()) {
            Response response = mailerProxy.sendMultipleEmail(emailDataList);
            LOGGER.info("openrota-mailer-service:" + response.getStatusInfo());
        }
    }

    @Override
//    @Scheduled(cron = CRON_TIME)
    public void projectClosureDueReminder() {
    }

    @Override
//    @Scheduled(cron = CRON_TIME)
    public void ProjectCompletionReminder() {
    }

    private List<EmailData> toEmailData(final List<Invitation> emailIds, final int days) {
        List<EmailData> emailDataList = new ArrayList<>();
        if (!emailIds.isEmpty()) {
            emailDataList = emailIds.parallelStream()
                    .map(invitation -> EmailData.builder()
                            .emailType(EmailType.INVITATION_EXPIRATION.value())
                            .mailTo(invitation.getEmailId())
                            .emailTemplateVariables(Map.of("expiresOn", invitation.getCreatedAt()
                                    .plusDays(days)
                                    .format(DateTimeFormatter.ISO_DATE)))
                            .build()).collect(Collectors.toList());
        } else {
            LOGGER.info("EmailData List is Empty for Invitation Expiration Reminder");
        }
        return emailDataList;
    }
}
