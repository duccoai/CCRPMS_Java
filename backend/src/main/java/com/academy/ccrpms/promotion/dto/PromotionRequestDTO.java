package com.academy.ccrpms.promotion.dto;

import lombok.Data;

@Data
public class PromotionRequestDTO {
    private int flightHours;
    private String performanceResult;
    private String certificates;
    private String teamLeadSuggestion;
}
