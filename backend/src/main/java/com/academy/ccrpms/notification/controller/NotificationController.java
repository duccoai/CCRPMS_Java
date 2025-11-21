package com.academy.ccrpms.notification.controller;

import com.academy.ccrpms.notification.entity.Notification;
import com.academy.ccrpms.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getNotifications(userId);
    }
}
