package com.academy.ccrpms.application.repository;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.application.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Truy vấn ứng viên
    List<Application> findByCandidate_Id(Long candidateId);

    // Truy vấn ứng dụng cho recruiter qua Job
    List<Application> findByJob_Recruiter_Id(Long recruiterId);

    // Count theo status
    long countByStatus(ApplicationStatus status);
}
