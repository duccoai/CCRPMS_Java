package com.academy.ccrpms.promotion.service;

import com.academy.ccrpms.promotion.dto.PromotionSummaryDTO;
import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.entity.PromotionStatus;
import com.academy.ccrpms.promotion.repository.PromotionApplicationRepository;
import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionApplicationRepository promotionRepo;
    private final UserRepository userRepo;

    // 1️⃣ Candidate gửi yêu cầu nâng bậc
    public PromotionApplication submitPromotion(Long userId, Integer flightHours,
                                                String performanceResult, String certificates,
                                                String teamLeadSuggestion) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Chặn nộp lần 2
        if (promotionRepo.existsByCandidate(user)) {
            throw new RuntimeException("Bạn chỉ được gửi yêu cầu nâng bậc 1 lần.");
        }

        PromotionApplication app = new PromotionApplication();
        app.setCandidate(user);
        app.setFlightHours(flightHours);
        app.setPerformanceResult(performanceResult);
        app.setCertificates(certificates);
        app.setTeamLeadSuggestion(teamLeadSuggestion);
        app.setStatus(PromotionStatus.PENDING); // ✅ Sử dụng PromotionStatus

        return promotionRepo.save(app);
    }

    // 2️⃣ Admin lấy toàn bộ promotion requests
    public List<PromotionSummaryDTO> getAllPromotionRequests() {
        return promotionRepo.findAll().stream().map(app -> {
            PromotionSummaryDTO dto = new PromotionSummaryDTO();
            dto.setId(app.getId());
            dto.setUserId(app.getCandidate().getId());
            dto.setUserFullName(app.getCandidate().getFullName());
            dto.setFlightHours(app.getFlightHours());
            dto.setPerformanceResult(app.getPerformanceResult());
            dto.setCertificates(app.getCertificates());
            dto.setTeamLeadSuggestion(app.getTeamLeadSuggestion());
            dto.setStatus(app.getStatus()); // ✅ Sử dụng PromotionStatus
            dto.setCreatedAt(app.getCreatedAt().toString());
            return dto;
        }).toList();
    }

    // 3️⃣ Admin update status (Approve / Reject)
    public PromotionApplication updateStatus(Long id, PromotionStatus newStatus) {
        PromotionApplication app = promotionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion request not found"));

        app.setStatus(newStatus); // ✅ Sử dụng PromotionStatus
        return promotionRepo.save(app);
    }

    // 4️⃣ Lấy hồ sơ gần nhất của candidate
    public PromotionApplication getLatestApplicationByCandidate(Long userId) {
        List<PromotionApplication> apps = promotionRepo.findByCandidate_IdOrderByCreatedAtDesc(userId);
        if (apps == null || apps.isEmpty()) return null;
        return apps.get(0);
    }

    // 5️⃣ Lấy tất cả hồ sơ của candidate
    public List<PromotionApplication> getApplicationsByCandidate(Long userId) {
        return promotionRepo.findByCandidate_IdOrderByCreatedAtDesc(userId);
    }
}
