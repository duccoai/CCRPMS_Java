package com.academy.ccrpms.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String roleName; // chỉ gửi tên role
}
