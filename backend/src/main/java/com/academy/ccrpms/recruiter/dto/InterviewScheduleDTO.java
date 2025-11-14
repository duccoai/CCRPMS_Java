package com.academy.ccrpms.recruiter.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewScheduleDTO {
    private Long applicationId;
    /**
     * interviewDate received as String (ISO-like). Example: "2025-11-14T14:30" or "2025-11-14T14:30:00"
     */
    private String interviewDate;
    private String location;
    private String note;
}
