package com.academy.ccrpms.recruiter.dto;

import java.time.OffsetDateTime;

import com.academy.ccrpms.recruiter.entity.Interview;

public class InterviewUpdateDTO {
    private Long applicationId;
    private String status;
    private OffsetDateTime schedule;
    private Double scoreInterview;
    private Double scoreExam;
    private String comment;
    // thêm
    private String location;
    private String note;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getSchedule() { return schedule; }
    public void setSchedule(OffsetDateTime schedule) { this.schedule = schedule; }

    public Double getScoreInterview() { return scoreInterview; }
    public void setScoreInterview(Double scoreInterview) { this.scoreInterview = scoreInterview; }

    public Double getScoreExam() { return scoreExam; }
    public void setScoreExam(Double scoreExam) { this.scoreExam = scoreExam; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Interview toEntity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }
}
