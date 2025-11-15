package com.academy.ccrpms.exam.entity;

import com.academy.ccrpms.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam extends BaseEntity {

    private String title;

    @Column(length = 2000)
    private String description;

    private int duration; // thời gian làm bài (phút)

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;

    // New field for active status
    private boolean active = true;

    // Getter and Setter for active field
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
