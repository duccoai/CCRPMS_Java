package com.academy.ccrpms.recruiter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationSummaryDTO {
    private Long applicationId;
    private String candidateUsername;
    private String candidateFullName;
    private String candidateEmail;
    private String jobTitle;
    private String status;
    private Long interviewId;
    private Long submissionId;
    private Double examScore;
    private Double interviewScore;
}
