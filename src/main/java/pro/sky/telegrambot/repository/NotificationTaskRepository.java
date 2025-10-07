package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    // Найти задачи по точному времени уведомления и которые ещё не отправлены
    List<NotificationTask> findByNotifyAtAndSentFalse(LocalDateTime notifyAt);
}