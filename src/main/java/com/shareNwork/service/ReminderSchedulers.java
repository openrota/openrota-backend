package com.shareNwork.service;

public interface ReminderSchedulers {

    void invitationExpireReminder();

    void projectClosureDueReminder();

    void ProjectCompletionReminder();
}
