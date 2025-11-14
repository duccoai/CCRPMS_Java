package com.academy.ccrpms.application.repository;

import com.academy.ccrpms.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser_Id(Long userId);
    List<Application> findByJob_Recruiter_Id(Long recruiterId);
}