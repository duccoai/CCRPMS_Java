package com.academy.ccrpms.user.repository;

import com.academy.ccrpms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByRole_Name(String roleName);

    List<User> findByRole_NameIn(List<String> roles); // dùng cho AdminService.getUsersByRoles
}
