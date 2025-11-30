package com.eeit205team5.rentalmanagement.security.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// OAuth2失敗處理器
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value("${app.frontend.url}")
    private String frontendUrl;

    // OAuth2/本地帳號登入失敗後的處理邏輯
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // 不要把exception.getMessage()放到URL(避免資訊外洩)
        String errorCode = mapExceptionToErrorCode(exception);

        // ?????
        String targetUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/auth/login")
                .queryParam("error", errorCode)
                .build()
                .toUriString();

        log.error("OAuth2登入失敗({}): {}", errorCode, exception.getMessage());

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 把不同AuthenticationException對應到統一錯誤代碼
    private String mapExceptionToErrorCode(AuthenticationException e) {
        if (e instanceof OAuth2AuthenticationException) {
            return "oauth2_error";
        }

        if (e instanceof BadCredentialsException) {
            return "bad_credentials";
        }

        if (e instanceof AccountExpiredException) {
            return "account_expired";
        }

        if (e instanceof DisabledException) {
            return "account_disabled";
        }

        if (e instanceof LockedException) {
            return "account_locked";
        }

        // 其他例外
        return "auth_failure";
    }
}