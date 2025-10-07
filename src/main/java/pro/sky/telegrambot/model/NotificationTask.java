package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "text", nullable = false, length = 1000)
    private String text;

    @Column(name = "notify_at", nullable = false)
    private LocalDateTime notifyAt;

    @Column(name = "sent", nullable = false)
    private boolean sent = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    // --- конструкторы, геттеры/сеттеры ---
    public NotificationTask() {}

    public NotificationTask(Long chatId, String text, LocalDateTime notifyAt) {
        this.chatId = chatId;
        this.text = text;
        this.notifyAt = notifyAt;
    }

    // getters and setters...
    public Long getId() { return id; }
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getNotifyAt() { return notifyAt; }
    public void setNotifyAt(LocalDateTime notifyAt) { this.notifyAt = notifyAt; }
    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}