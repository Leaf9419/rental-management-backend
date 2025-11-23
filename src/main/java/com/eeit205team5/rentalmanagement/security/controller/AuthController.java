package com.eeit205team5.rentalmanagement.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.eeit205team5.rentalmanagement.security.dto.request.LoginRequest;
import com.eeit205team5.rentalmanagement.security.dto.request.RegisterRequest;
import com.eeit205team5.rentalmanagement.security.dto.response.AuthResponse;
import com.eeit205team5.rentalmanagement.security.dto.response.UserInfoResponse;
import com.eeit205team5.rentalmanagement.security.entity.User;
import com.eeit205team5.rentalmanagement.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    /**
     * 註冊API
     * POST /auth/register
     * 
     * @param request
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = service.register(request);

        return ApiResponse.success(response, "成功");
    }

    /**
     * 登入API
     * POST /auth/login
     * 
     * @param request
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = service.login(request);

        return ApiResponse.success(response, "成功");
    }

    /**
     * 獲取當前登入使用者資訊
     * GET /auth/me
     * 需要在HTTP header帶上Authorization: Bearer {token}
     * 
     * @return 200 OK + ApiResponse<UserInfoResponse>或ApiResponse<?>錯誤訊息
     */
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserInfoResponse> getCurrentUser() {
        User user = service.getCurrentUser();

        UserInfoResponse response = UserInfoResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .status(user.getStatus())
                .emailVerified(user.getEmailVerified())
                .phoneVerified(user.getPhoneVerified())
                .createdAt(user.getCreatedAt())
                .build();

        return ApiResponse.success(response, "成功");
    }
}