package com.academy.ccrpms.recruiter.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewScheduleDTO {
    private Long applicationId;
    private LocalDateTime interviewDate;
    private String location;
    private String note;
}
