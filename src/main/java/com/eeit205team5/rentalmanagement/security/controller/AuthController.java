package com.eeit205team5.rentalmanagement.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.eeit205team5.rentalmanagement.security.dto.request.LoginRequest;
import com.eeit205team5.rentalmanagement.security.dto.request.RegisterRequest;
import com.eeit205team5.rentalmanagement.security.dto.request.VerifyEmailRequest;
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
    private final AuthService authService;

    /**
     * 註冊
     * POST /auth/register
     * 
     * @param request
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        // Controller層簡單驗證
        // 驗證角色(避免惡意傳入admin)
        if (!"tenant".equals(request.getRole()) && !"landlord".equals(request.getRole())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("角色只能是tenant或landlord"));
        }

        AuthResponse response = authService.register(request);

        return ResponseEntity.ok(ApiResponse.success(response, "註冊成功，驗證碼已發送到您的Email"));
    }

    /**
     * 驗證Email
     * POST /auth/verify-email
     * 
     * @param request
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        authService.verifyEmail(request.getCode());

        return ApiResponse.success("Email驗證成功");
    }

    /**
     * 重新發送Email驗證碼
     * POST /auth/resend-email-verification-code
     * 
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/resend-email-verification-code")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> resendEmailVerificationCode() {
        authService.resendEmailVerificationCode();

        return ApiResponse.success("驗證碼已重新發送");
    }

    /**
     * 登入
     * POST /auth/login
     * 
     * @param request
     * @return 200 OK + ApiResponse<AuthResponse>物件或ApiResponse<?>錯誤訊息
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        return ApiResponse.success(response, "登入成功");
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
        User user = authService.getCurrentUser();

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