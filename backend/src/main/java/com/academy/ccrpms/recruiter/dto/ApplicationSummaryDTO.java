package com.academy.ccrpms.recruiter.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationSummaryDTO {
    private Long applicationId;
    private String candidateName;
    private String candidateEmail;
    private String jobTitle;
    private String status;
    private Double examScore;
    private Double interviewScore;
}
