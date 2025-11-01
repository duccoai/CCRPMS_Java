package com.academy.ccrpms.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponseDTO {
    private Long submissionId;
    private Double score;
    private String testTitle;
    private String testDescription;
    private String userName;
    private String userEmail;
    private String applicationStatus;
}
