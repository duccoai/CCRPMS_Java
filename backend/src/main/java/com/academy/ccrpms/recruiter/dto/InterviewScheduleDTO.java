package com.academy.ccrpms.recruiter.dto;

import com.academy.ccrpms.recruiter.entity.Interview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewScheduleDTO {
    private Long applicationId;
    private LocalDateTime interviewDate;
    private String location;
    private String note; // optional

    public Interview toEntity() {
        Interview interview = new Interview();
        interview.setInterviewDate(this.interviewDate);
        interview.setLocation(this.location);
        interview.setNote(this.note);
        return interview;
    }
}
