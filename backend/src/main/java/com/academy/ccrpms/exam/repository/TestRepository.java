package com.academy.ccrpms.exam.repository;

import com.academy.ccrpms.exam.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {}
