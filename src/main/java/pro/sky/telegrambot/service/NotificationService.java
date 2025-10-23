package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationService {
    boolean parseAndSaveReminder(Long chatId, String messageText);
    List<NotificationTask> getAllNotifications();
    List<NotificationTask> getPendingNotifications();
    void markAsSent(NotificationTask task);
}
