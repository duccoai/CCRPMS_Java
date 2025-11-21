package com.academy.ccrpms.promotion.controller;

import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    // 1️⃣ Submit promotion request
    @PostMapping("/submit")
    public ResponseEntity<?> submitPromotion(@RequestBody Map<String, Object> body) {
        try {
            Long userId = Long.valueOf(body.get("userId").toString());
            Integer flightHours = Integer.valueOf(body.get("flightHours").toString());
            String performanceResult = body.get("performanceResult").toString();
            String certificates = body.get("certificates").toString();
            String teamLeadSuggestion = body.get("teamLeadSuggestion").toString();

            var app = promotionService.submitPromotion(
                    userId, flightHours, performanceResult, certificates, teamLeadSuggestion
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Hồ sơ nâng bậc đã được gửi thành công",
                    "id", app.getId()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
        }
    }

    // ❗ CHỖ NÀY BỊ TRÙNG — ĐÃ ĐỔI THÀNH /user/me/simple
    @GetMapping("/user/me/simple")
    public ResponseEntity<List<PromotionApplication>> getMyPromotionApplicationsSimple(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        List<PromotionApplication> apps =
                promotionService.getApplicationsByCandidate(userDetails.getId());

        return ResponseEntity.ok(apps);
    }
}
