package com.academy.ccrpms.auth.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.academy.ccrpms.auth.model.CustomUserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = userDetails.getRoleName(); // "ADMIN", "CANDIDATE", "RECRUITER"

        // Redirect theo role
        if ("ADMIN".equals(role)) {
            response.sendRedirect("/admin/dashboard");
        } else if ("CANDIDATE".equals(role)) {
            response.sendRedirect("/candidate/jobs");
        } else if ("RECRUITER".equals(role)) {
            response.sendRedirect("/recruiter/dashboard");
        } else {
            response.sendRedirect("/"); // fallback
        }
    }
}
