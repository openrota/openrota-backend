package com.shareNwork.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.shareNwork.constants.Constants;
import com.shareNwork.service.EmailService;

import org.jboss.logging.Logger;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.vertx.ConsumeEvent;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = Logger.getLogger(EmailServiceImpl.class);

    @Inject
    ReactiveMailer mailer;

    @Override
    @ConsumeEvent(value = Constants.SEND_MAIL_EVENT)
    public void sendEmail(final Mail mail) {
        mailer.send(mail).subscribe().with(t -> LOG.info("Sent mail to" + mail.getTo()));
    }
}
