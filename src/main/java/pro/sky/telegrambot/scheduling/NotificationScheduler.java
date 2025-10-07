package pro.sky.telegrambot.scheduling;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationService;

import java.util.List;

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;
    private final TelegramBot bot;

    public NotificationScheduler(NotificationService notificationService, TelegramBot bot) {
        this.notificationService = notificationService;
        this.bot = bot;
    }

    @Scheduled(cron = "0 0/1 * * * *") // каждую минуту
    public void sendDueNotifications() {
        List<NotificationTask> due = notificationService.getPendingNotifications();
        for (NotificationTask task : due) {
            try {
                bot.execute(new SendMessage(task.getChatId(), task.getText()));
                notificationService.markAsSent(task);
            } catch (Exception e) {
                System.err.println("Ошибка отправки уведомления: " + e.getMessage());
            }
        }
    }
}