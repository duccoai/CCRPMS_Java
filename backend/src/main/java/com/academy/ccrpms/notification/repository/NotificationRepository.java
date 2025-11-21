package com.academy.ccrpms.notification.repository;

import com.academy.ccrpms.notification.entity.Notification;
import com.academy.ccrpms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
