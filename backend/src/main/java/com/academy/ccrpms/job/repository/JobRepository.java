package com.academy.ccrpms.job.repository;

import com.academy.ccrpms.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiter_Id(Long recruiterId);
}


