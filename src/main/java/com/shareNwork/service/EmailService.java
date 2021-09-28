package com.shareNwork.service;

import io.quarkus.mailer.Mail;

public interface EmailService {

    void sendEmail(final Mail mail);
}
