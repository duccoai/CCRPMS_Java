package com.academy.ccrpms.application.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.job.entity.Job;
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

    // Ứng viên nộp hồ sơ
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Vị trí tuyển dụng
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // Trạng thái hồ sơ
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;
}
