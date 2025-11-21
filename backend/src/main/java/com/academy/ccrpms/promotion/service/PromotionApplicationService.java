package com.academy.ccrpms.promotion.service;

import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.promotion.entity.PromotionStatus;
import com.academy.ccrpms.promotion.repository.PromotionApplicationRepository;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionApplicationService {

    private final PromotionApplicationRepository promotionApplicationRepository;
    private final UserRepository userRepository;

    // Lấy danh sách hồ sơ nâng bậc của user đang đăng nhập
    public List<PromotionApplication> getMyPromotionApplications(Long userId) {
        return promotionApplicationRepository.findByCandidate_Id(userId);
    }

    // Recruiter cập nhật trạng thái hồ sơ thăng chức
    @Transactional
    public void updateStatus(Long id, String status) {
        PromotionApplication app = promotionApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ với id: " + id));

        try {
            PromotionStatus newStatus = PromotionStatus.valueOf(status.toUpperCase());
            app.setStatus(newStatus);
            promotionApplicationRepository.save(app);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Trạng thái không hợp lệ: " + status);
        }
    }
}
