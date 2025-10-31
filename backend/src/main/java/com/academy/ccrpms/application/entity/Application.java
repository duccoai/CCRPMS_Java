package com.academy.ccrpms.application.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người nộp hồ sơ (Ứng viên)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Tạm thời để text, sau này sẽ là entity Job
    @Column(nullable = false)
    private String jobTitle;

    // Trạng thái hồ sơ
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
}
