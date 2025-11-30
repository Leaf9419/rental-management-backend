package com.eeit205team5.rentalmanagement.security.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;

@RestController
@RequestMapping("/auth/oauth2")
public class OAuth2Controller {
    /**
     * 取得OAuth2登入URL
     * GET /auth/oauth2/authorize/{provider}
     * 
     * @param provider
     * @return 200 OK + ApiResponse<Map<String, Object>>物件
     */
    @GetMapping("/authorize/{provider}")
    public ApiResponse<Map<String, Object>> getAuthorizationUrl(@PathVariable String provider) {
        String authUrl = "http://localhost:8080/oauth2/authorization/" + provider;

        return ApiResponse.success(Map.of(
                "provider", provider,
                "authorizationUrl", authUrl));
    }
}