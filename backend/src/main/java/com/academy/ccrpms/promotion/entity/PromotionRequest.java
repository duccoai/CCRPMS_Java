package com.academy.ccrpms.promotion.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PromotionRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int flightHours;

    private String performanceResult;

    private String certificates;

    private String teamLeadSuggestion;

    @Enumerated(EnumType.STRING)
    private PromotionStatus status;
}
