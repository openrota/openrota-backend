package com.shareNwork.service;

public interface ReminderService {

    void callReminders();

    void invitationExpireReminder();

    void projectClosureDueReminder();

    void ProjectCompletionReminder();
}
