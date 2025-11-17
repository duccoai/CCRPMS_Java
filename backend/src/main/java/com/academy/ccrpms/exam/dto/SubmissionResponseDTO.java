package com.academy.ccrpms.exam.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionResponseDTO {
    private Long submissionId;
    private Double score;
    private String examDescription;
    private String userName;
    private String userEmail;
    private String applicationStatus;
}
