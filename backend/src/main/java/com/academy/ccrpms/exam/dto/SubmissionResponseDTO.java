package com.academy.ccrpms.exam.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SubmissionResponseDTO {
    private Long submissionId;
    private String examDescription; // ← thêm
    private String userName;
    private String userEmail;
    private double score;
    private String applicationStatus;
}
