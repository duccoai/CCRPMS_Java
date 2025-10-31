package com.academy.ccrpms.job.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.application.entity.Application;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    @Column(name = "salary_range")
    private String salaryRange;

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.OPEN;

    // Danh sách hồ sơ ứng tuyển cho vị trí này
    @OneToMany(mappedBy = "job")
    private List<Application> applications;
}
