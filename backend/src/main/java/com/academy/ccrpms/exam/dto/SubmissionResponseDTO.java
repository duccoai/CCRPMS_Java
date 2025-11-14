package com.academy.ccrpms.exam.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SubmissionResponseDTO {

    private Long submissionId;
    private Long userId;
    private Long applicationId;
    private Double score;
    private OffsetDateTime createdAt;
    private Object answers; // giữ raw answers hoặc DTO
}
