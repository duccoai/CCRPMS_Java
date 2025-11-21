package com.academy.ccrpms.notification.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")   // ✔ FIX: lưu message dài, không bị cắt
    private String message;

    private boolean readStatus = false;
}
