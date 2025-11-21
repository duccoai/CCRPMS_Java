package com.academy.ccrpms.recruiter.dto;

import com.academy.ccrpms.recruiter.entity.Interview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewScheduleDTO {

    private Long applicationId;

    // 🔥 Sửa: dùng 'date' thay vì 'interviewDate'
    // Và dùng LocalDateTime để Spring/Jackson parse ISO-8601 tự động
    private LocalDateTime date;

    private String location;

    private String note;

    public Interview toEntity() {
        Interview interview = new Interview();

        // 🔥 Sửa theo field mới
        interview.setInterviewDate(this.date);

        interview.setLocation(this.location);
        interview.setNote(this.note);

        return interview;
    }
}
