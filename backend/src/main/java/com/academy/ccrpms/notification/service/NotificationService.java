package com.academy.ccrpms.notification.service;

import com.academy.ccrpms.notification.entity.Notification;
import com.academy.ccrpms.notification.repository.NotificationRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void sendNotification(Long userId, String title, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification noti = new Notification();
        noti.setUser(user);
        noti.setTitle(title);
        noti.setMessage(message);

        notificationRepository.save(noti);
    }

    public List<Notification> getNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
