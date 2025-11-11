package com.academy.ccrpms.exam.repository;

import com.academy.ccrpms.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
