package com.academy.ccrpms.exam.repository;

import com.academy.ccrpms.exam.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser_Id(Long userId);
    Optional<Submission> findFirstByApplication_IdOrderByCreatedAtDesc(Long applicationId);
}
