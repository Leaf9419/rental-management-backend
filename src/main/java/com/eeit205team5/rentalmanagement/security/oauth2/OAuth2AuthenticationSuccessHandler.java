package com.eeit205team5.rentalmanagement.security.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.eeit205team5.rentalmanagement.security.basic.CustomUserDetails;
import com.eeit205team5.rentalmanagement.security.basic.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// OAuth2成功處理器
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    // @Value("${app.oauth2.authorized-redirect-uris}")
    // private String authorizedRedirectUri;

    // 計算登入成功後要導向哪個頁面(URL)
    // 通常會依據使用者角色、參數或預設邏輯決定導向哪裡
    protected String determineTargetUrl(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        // 生成JWT
        String token = jwtTokenProvider.generateToken(authentication);

        // 取得使用者資訊
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // 重導向前端，建立URL builder，在URL後方加上query string
        return UriComponentsBuilder.fromUriString(frontendUrl) // 改frontendUrl
                .path("/auth/login") // 改加上登入頁面路徑
                .queryParam("token", token)
                .queryParam("userId", customUserDetails.getUserId())
                // .queryParam("email", customUserDetails.getEmail())
                .queryParam("role", customUserDetails.getRole())
                .build() // 把builder轉換成URL物件
                .toUriString(); // 把URL物件轉換成字串
    }

    // OAuth2/本地帳號登入成功後的處理邏輯
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        // isCommitted():判斷response是否已經送出給用戶端，避免重複修改header或重定向
        if (response.isCommitted()) {
            log.debug("response已經被提交，無法導向到: {}", targetUrl);

            return;
        }

        // 清除登入過程中暫存的敏感資訊(如:OAuth2登入過程的session attributes、錯誤訊息等)
        clearAuthenticationAttributes(request);

        // 真正把使用者導向targetUrl
        // getRedirectStrategy():預設是DefaultRedirectStrategy
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}