package com.academy.ccrpms.job.dto;

public record JobRequestDTO(
    String title,
    String description,
    String location,
    String salaryRange,
    String status,    // optional, name of enum JobStatus
    Long recruiterId  // optional: id của recruiter (user)
) {}
