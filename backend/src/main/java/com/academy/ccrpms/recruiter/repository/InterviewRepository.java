package com.academy.ccrpms.recruiter.repository;

import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    /**
     * Lấy 1 interview theo application (nếu có)
     */
    Optional<Interview> findByApplication(Application application);

    /**
     * Lấy interview mới nhất theo ngày phỏng vấn
     */
    Optional<Interview> findFirstByApplicationOrderByInterviewDateDesc(Application application);

    /**
     * Lấy danh sách interview theo applicationId
     */
    List<Interview> findByApplication_Id(Long applicationId);

    /**
     * (Tuỳ chọn) Lấy tất cả interview thuộc các job của recruiter
     * nếu muốn Admin/Recruiter có dashboard phỏng vấn chung
     */
    List<Interview> findByApplication_Job_Recruiter_Id(Long recruiterId);
}
