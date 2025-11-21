package com.academy.ccrpms.promotion.dto;

import com.academy.ccrpms.promotion.entity.PromotionStatus;
import lombok.Data;

@Data
public class PromotionUpdateDTO {
    private PromotionStatus status;
}
