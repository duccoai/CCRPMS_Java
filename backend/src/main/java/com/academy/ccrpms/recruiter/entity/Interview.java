package com.academy.ccrpms.recruiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "interviews")
public class Interview {

    public enum InterviewStatus {
        SCHEDULED,
        COMPLETED,
        PENDING,
        INTERVIEWING,
        APPROVED,
        REJECTED,
        HIRED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private com.academy.ccrpms.application.entity.Application application;

    // 🔥 SỬA CHUẨN – KHỚP CỘT TRONG DATABASE
    @Column(name = "interview_date", nullable = false)
    private LocalDateTime interviewDate;

    private String location;

    private String comment;

    private Double score;        // interview score
    private Double scoreExam;    // exam score

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @Column(name = "note")
    private String note;  // recruiter's note
}
