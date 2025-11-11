package com.academy.ccrpms.user.entity;

import com.academy.ccrpms.user.entity.User;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private boolean active = true;

    // 🟩 Thông tin hồ sơ bổ sung
    private String avatarUrl;
    private String cvUrl;
    @Column(length = 2000)
    private String bio;
}
