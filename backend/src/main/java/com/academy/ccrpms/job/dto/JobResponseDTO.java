package com.academy.ccrpms.job.dto;

import com.academy.ccrpms.user.dto.UserSummaryDTO;
import com.academy.ccrpms.job.entity.Job;

public record JobResponseDTO(
    Long id,
    String title,
    String description,
    String location,
    String salaryRange,
    String status,
    UserSummaryDTO recruiter
) {
    public static JobResponseDTO fromEntity(Job job) {
        if (job == null) return null;
        return new JobResponseDTO(
            job.getId(),
            job.getTitle(),
            job.getDescription(),
            job.getLocation(),
            job.getSalaryRange(),
            job.getStatus() != null ? job.getStatus().name() : null,
            UserSummaryDTO.fromEntity(job.getRecruiter())
        );
    }
}
