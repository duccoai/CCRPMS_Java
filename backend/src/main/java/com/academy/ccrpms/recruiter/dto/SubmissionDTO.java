package com.academy.ccrpms.recruiter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long submissionId;
    private Long applicationId;
    private double score;
    private String feedback;
}
