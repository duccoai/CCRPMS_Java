package com.academy.ccrpms.recruiter.repository;

import com.academy.ccrpms.recruiter.entity.Interview;
import com.academy.ccrpms.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<Interview> findByApplication(Application application);

    // lấy interview mới nhất theo application (theo interviewDate)
    Optional<Interview> findFirstByApplicationOrderByInterviewDateDesc(Application application);

    // nếu cần danh sách
    List<Interview> findByApplication_Id(Long applicationId);
}
