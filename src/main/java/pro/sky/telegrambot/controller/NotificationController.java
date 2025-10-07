package pro.sky.telegrambot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NotificationTask>> getAll() {
        return ResponseEntity.ok(service.getAllNotifications());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificationTask>> getPending() {
        return ResponseEntity.ok(service.getPendingNotifications());
    }

    @PostMapping
    public ResponseEntity<String> addNotification(@RequestParam Long chatId, @RequestParam String text) {
        boolean ok = service.parseAndSaveReminder(chatId, text);
        if (ok) return ResponseEntity.ok("Notification saved successfully");
        else return ResponseEntity.badRequest().body("Invalid format. Use: dd.MM.yyyy HH:mm <text>");
    }
}
