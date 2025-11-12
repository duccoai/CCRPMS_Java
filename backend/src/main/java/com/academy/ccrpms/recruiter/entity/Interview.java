package com.academy.ccrpms.recruiter.entity;

import com.academy.ccrpms.application.entity.Application;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private LocalDateTime interviewDate;
    private String location;
    private Double score;
    private String comment;
    private String note;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    public enum InterviewStatus {
        SCHEDULED, COMPLETED, CANCELED
    }
}
