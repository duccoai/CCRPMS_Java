package com.academy.ccrpms.user.service;

import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 🟩 Xem hồ sơ người dùng
    public User getProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🟩 Cập nhật thông tin hồ sơ (tên, email, avatar, bio, cv)
    public User updateProfile(Long userId, User updatedData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedData.getFullName() != null) user.setFullName(updatedData.getFullName());
        if (updatedData.getEmail() != null) user.setEmail(updatedData.getEmail());
        if (updatedData.getAvatarUrl() != null) user.setAvatarUrl(updatedData.getAvatarUrl());
        if (updatedData.getBio() != null) user.setBio(updatedData.getBio());
        if (updatedData.getCvUrl() != null) user.setCvUrl(updatedData.getCvUrl());

        return userRepository.save(user);
    }

    // 🟩 Đổi mật khẩu
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User updateAvatar(Long userId, String avatarUrl) {
        User user = getProfile(userId);
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    public User updateCv(Long userId, String cvUrl) {
        User user = getProfile(userId);
        user.setCvUrl(cvUrl);
        return userRepository.save(user);
    }

}
