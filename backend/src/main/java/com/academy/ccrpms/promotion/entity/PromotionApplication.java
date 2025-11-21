package com.academy.ccrpms.promotion.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "promotion_applications")
public class PromotionApplication extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    @Column(nullable = false)
    private Integer flightHours;

    @Column(length = 500)
    private String performanceResult;

    @Column(length = 500)
    private String certificates;

    @Column(length = 500)
    private String teamLeadSuggestion;

    @Enumerated(EnumType.STRING)
    private PromotionStatus status = PromotionStatus.PENDING;

    private Double scoreExam; // nếu muốn kết hợp với exam
}
