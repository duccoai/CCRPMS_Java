package com.academy.ccrpms.promotion.controller;

import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.service.PromotionApplicationService;
import com.academy.ccrpms.auth.model.CustomUserDetails;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionApplicationController {

    private final PromotionApplicationService promotionApplicationService;
    private final UserRepository userRepository;

    /**
     * 1️⃣ User lấy danh sách hồ sơ thăng chức của chính mình
     */
    @GetMapping("/user/me")
    public ResponseEntity<?> getMyPromotionApplications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Long userId = userDetails.getId();
        List<PromotionApplication> list =
                promotionApplicationService.getMyPromotionApplications(userId);

        return ResponseEntity.ok(list);
    }

    /**
     * 3️⃣ Recruiter cập nhật trạng thái hồ sơ
     */
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        try {
            String status = body.get("status");

            promotionApplicationService.updateStatus(id, status);

            return ResponseEntity.ok(
                    Map.of("message", "Cập nhật trạng thái thành công")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
