package com.academy.ccrpms.user.dto;

import com.academy.ccrpms.user.entity.User;

public record UserSummaryDTO(
    Long id,
    String username,
    String fullName,
    String avatarUrl,
    String email
) {
    public static UserSummaryDTO fromEntity(User user) {
        if (user == null) return null;
        return new UserSummaryDTO(
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            user.getAvatarUrl(),
            user.getEmail()
        );
    }
}
