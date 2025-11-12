package com.academy.ccrpms.exam.repository;

import com.academy.ccrpms.exam.entity.Submission;
import com.academy.ccrpms.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    // 🟢 Lấy danh sách bài thi theo hồ sơ ứng tuyển
    List<Submission> findByApplication(Application application);

    // 🟢 Dùng cho ApplicationService (đã có sẵn)
    List<Submission> findByUserId(Long userId);
}
