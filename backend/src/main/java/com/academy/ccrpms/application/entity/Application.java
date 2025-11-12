package com.academy.ccrpms.application.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.job.entity.Job;
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
    @JoinColumn(name = "user_id")
    private User user;

    // Vị trí ứng tuyển
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job; // phải import com.academy.ccrpms.job.entity.Job

    // Trạng thái hồ sơ
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

}
