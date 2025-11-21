package com.academy.ccrpms.recruiter.controller;

import com.academy.ccrpms.promotion.dto.PromotionSummaryDTO;
import com.academy.ccrpms.promotion.dto.PromotionUpdateDTO;
import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/promotions")
@RequiredArgsConstructor
public class PromotionRecruiterController {

    private final PromotionService promotionService;

    /**
     * 1️⃣ Lấy danh sách tất cả yêu cầu thăng chức
     */
    @GetMapping("/list")
    public List<PromotionSummaryDTO> getPromotionList() {
        return promotionService.getAllPromotionRequests();
    }

    /**
     * 2️⃣ Recruiter cập nhật trạng thái yêu cầu thăng chức
     */
    @PutMapping("/{id}/status")
    public PromotionApplication updateStatus(
            @PathVariable Long id,
            @RequestBody PromotionUpdateDTO dto
    ) {
        return promotionService.updateStatus(id, dto.getStatus());
    }
}
