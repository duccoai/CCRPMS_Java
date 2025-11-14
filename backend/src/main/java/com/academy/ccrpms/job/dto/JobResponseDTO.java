package com.academy.ccrpms.job.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String salaryRange;
    private String status;
    private RecruiterLite recruiter;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruiterLite {
        private Long id;
        private String username;
        private String email;
    }

    public static JobResponseDTO fromEntity(com.academy.ccrpms.job.entity.Job job) {
        if (job == null) return null;
        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .salaryRange(job.getSalaryRange())
                .status(job.getStatus() != null ? job.getStatus().name() : null)
                .recruiter(job.getRecruiter() == null ? null : new RecruiterLite(
                        job.getRecruiter().getId(),
                        job.getRecruiter().getUsername(),
                        job.getRecruiter().getEmail()
                ))
                .build();
    }
}
