package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationService;

import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot bot;
    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot bot, NotificationService notificationService) {
        this.bot = bot;
        this.notificationService = notificationService;
        this.bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() == null || update.message().text() == null) continue;

            Long chatId = update.message().chat().id();
            String text = update.message().text().trim();

            if (text.equals("/start")) {
                bot.execute(new SendMessage(chatId, "Привет! Отправь напоминание в формате: 01.01.2022 20:00 Сделать домашнюю работу"));
                continue;
            }

            boolean saved = notificationService.parseAndSaveReminder(chatId, text);
            if (saved)
                bot.execute(new SendMessage(chatId, "✅ Напоминание сохранено!"));
            else
                bot.execute(new SendMessage(chatId, "❌ Формат неверный. Используй: 01.01.2022 20:00 Текст"));
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
