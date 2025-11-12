package com.academy.ccrpms.auth.model;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private RoleResponse role;

    public UserResponse(Long id, String username, String fullName, RoleResponse role) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }

    @Data
    public static class RoleResponse {
        private Integer id;
        private String name;

        // ✅ Thêm constructor nhận id + name
        public RoleResponse(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
