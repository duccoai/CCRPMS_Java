package com.academy.ccrpms.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text; // đáp án người chọn

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // liên kết với Submission
    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;
}
