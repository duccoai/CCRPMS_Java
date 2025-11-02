package com.academy.ccrpms.user.controller;

import com.academy.ccrpms.user.entity.User;
import com.academy.ccrpms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;


import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 🟩 Lấy hồ sơ người dùng
    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    // 🟩 Cập nhật hồ sơ
    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable Long userId, @RequestBody User updatedData) {
        return ResponseEntity.ok(userService.updateProfile(userId, updatedData));
    }

    // 🟩 Đổi mật khẩu
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> passwords
    ) {
        try {
            String oldPassword = passwords.get("oldPassword");
            String newPassword = passwords.get("newPassword");
            userService.changePassword(userId, oldPassword, newPassword);
            return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/upload/avatar/{userId}")
    public ResponseEntity<?> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String uploadDir = "uploads/avatars/";
        new File(uploadDir).mkdirs();

        String filePath = uploadDir + userId + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        User user = userService.updateAvatar(userId, filePath);
        return ResponseEntity.ok(Map.of("message", "Avatar uploaded successfully", "avatarUrl", filePath));
    }

    @PostMapping("/upload/cv/{userId}")
    public ResponseEntity<?> uploadCv(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String uploadDir = "uploads/cv/";
        new File(uploadDir).mkdirs();

        String filePath = uploadDir + userId + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        User user = userService.updateCv(userId, filePath);
        return ResponseEntity.ok(Map.of("message", "CV uploaded successfully", "cvUrl", filePath));
    }

}
