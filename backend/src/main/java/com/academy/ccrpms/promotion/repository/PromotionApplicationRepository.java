package com.academy.ccrpms.promotion.repository;

import com.academy.ccrpms.promotion.entity.PromotionApplication;
import com.academy.ccrpms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionApplicationRepository extends JpaRepository<PromotionApplication, Long> {

    // Lấy tất cả hồ sơ của candidate theo thời gian giảm dần
    List<PromotionApplication> findByCandidate_IdOrderByCreatedAtDesc(Long candidateId);

    // Kiểm tra candidate đã nộp hồ sơ chưa
    boolean existsByCandidate(User candidate);

    // Lấy danh sách hồ sơ theo candidate (không cần sắp xếp)
    List<PromotionApplication> findByCandidate_Id(Long candidateId);
}
