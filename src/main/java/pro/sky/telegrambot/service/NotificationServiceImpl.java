package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTaskRepository repository;
    private final Pattern PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})\\s+(.+)", Pattern.DOTALL);
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public NotificationServiceImpl(NotificationTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public boolean parseAndSaveReminder(Long chatId, String messageText) {
        Matcher matcher = PATTERN.matcher(messageText.trim());
        if (!matcher.matches()) {
            return false;
        }

        String dateTimeStr = matcher.group(1);
        String reminderText = matcher.group(2).trim();

        LocalDateTime notifyAt = LocalDateTime.parse(dateTimeStr, FORMATTER)
                .truncatedTo(ChronoUnit.MINUTES);

        NotificationTask task = new NotificationTask(chatId, reminderText, notifyAt);
        repository.save(task);
        return true;
    }

    @Override
    public List<NotificationTask> getAllNotifications() {
        return repository.findAll();
    }

    @Override
    public List<NotificationTask> getPendingNotifications() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return repository.findByNotifyAtAndSentFalse(now);
    }

    @Override
    @Transactional
    public void markAsSent(NotificationTask task) {
        task.setSent(true);
        repository.save(task);
    }
}