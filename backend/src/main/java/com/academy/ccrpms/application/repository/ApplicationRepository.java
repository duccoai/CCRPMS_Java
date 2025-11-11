package com.academy.ccrpms.application.repository;

import com.academy.ccrpms.application.entity.Application;
import com.academy.ccrpms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // ✅ thêm dòng này

import java.util.List;

@Repository  // ✅ thêm dòng này
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByUserId(Long userId);
}
