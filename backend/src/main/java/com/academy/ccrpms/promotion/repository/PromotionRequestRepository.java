package com.academy.ccrpms.promotion.repository;

import com.academy.ccrpms.promotion.entity.PromotionRequest;
import com.academy.ccrpms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRequestRepository extends JpaRepository<PromotionRequest, Long> {

    boolean existsByUser(User user);  // 1 user chỉ nộp 1 lần

    Optional<PromotionRequest> findByUser(User user);
}
