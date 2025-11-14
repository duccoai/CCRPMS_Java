package com.academy.ccrpms.recruiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Interview {

    public enum InterviewStatus {
        SCHEDULED, COMPLETED, PENDING, INTERVIEWING, APPROVED, REJECTED, HIRED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private com.academy.ccrpms.application.entity.Application application;

    private LocalDateTime interviewDate;
    private String location;
    private String comment;
    private Double score;       // score interview
    private Double scoreExam;   // score exam
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
    @Column(name = "note")
    private String note;  // comment của recruiter

}
