package com.academy.ccrpms.application.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.job.entity.Job;
import com.academy.ccrpms.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"applications", "jobs"})
    private User candidate;

    // Vị trí ứng tuyển
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    @JsonIgnoreProperties({"recruiter", "applications"})
    private Job job;

    // Trạng thái hồ sơ
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    // URL CV (nếu có)
    private String cvUrl;

}
