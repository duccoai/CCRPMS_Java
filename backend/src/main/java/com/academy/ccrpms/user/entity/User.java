package com.academy.ccrpms.user.entity;

import com.academy.ccrpms.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private boolean active = true;

    // 🟩 Thông tin hồ sơ bổ sung
    private String avatarUrl; // ảnh đại diện
    private String cvUrl;     // đường dẫn CV
    @Column(length = 2000)
    private String bio;       // mô tả cá nhân, giới thiệu ngắn
}
