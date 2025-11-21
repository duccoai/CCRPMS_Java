package com.academy.ccrpms.promotion.dto;

import com.academy.ccrpms.promotion.entity.PromotionStatus;
import lombok.Data;

@Data
public class PromotionSummaryDTO {
    private Long id;
    private Long userId;
    private String userFullName;
    private int flightHours;
    private String performanceResult;
    private String certificates;
    private String teamLeadSuggestion;

    // Sử dụng enum PromotionStatus
    private PromotionStatus status;

    private String createdAt;
}
