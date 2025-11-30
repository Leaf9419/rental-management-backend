package com.eeit205team5.rentalmanagement.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eeit205team5.rentalmanagement.PermitUrlJohn;
import com.eeit205team5.rentalmanagement.PermitUrlLeaf;
import com.eeit205team5.rentalmanagement.PermitUrlNa;
import com.eeit205team5.rentalmanagement.security.basic.JwtAuthenticationFilter;
import com.eeit205team5.rentalmanagement.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.eeit205team5.rentalmanagement.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.eeit205team5.rentalmanagement.security.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // 未來擴充RBAC會用到
@EnableWebSecurity // 要完全覆蓋預設安全模型，或使用WebSecurityCustomizer才需要
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    // 提供全域PasswordEncoder bean，加密和比對密碼
    // 這裡指定使用BCryptPasswordEncoder實作類別
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 用於自動注入PasswordEncoder passwordEncoder
    }

    // 取得Spring Security內建配置好的AuthenticationManager(認證提供者)
    // 帳密登入用
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // security filter chain設定
    // HttpSecurity:Spring Security HTTP設定面板，用來配置安全規則
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Spring Security預設會檢查POST、PUT、DELETE是否有CSRF token
                // 停用CSRF(因為使用JWT)
                .csrf(csrf -> csrf.disable())

                // Security層CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Session管理:無狀態
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 授權設定
                .authorizeHttpRequests(auth -> {
                    // 公開的API(不需要認證)
                    auth.requestMatchers(
                            "/auth/register",
                            "/auth/login",
                            "/auth/oauth2/**", // OAuth2 callback
                            "/oauth2/**", // OAuth2 endpoints
                            "/login/oauth2/*") // OAuth2 login
                            .permitAll();

                    // 公開的API(不需要認證) for test
                    if (PermitUrlLeaf.URLS.length > 0) {
                        auth.requestMatchers(PermitUrlLeaf.URLS).permitAll();
                    }
                    if (PermitUrlNa.URLS.length > 0) {
                        auth.requestMatchers(PermitUrlNa.URLS).permitAll();
                    }
                    if (PermitUrlJohn.URLS.length > 0) {
                        auth.requestMatchers(PermitUrlJohn.URLS).permitAll();
                    }

                    // 其他所有請求都需要認證
                    auth.anyRequest().authenticated();
                })

                // OAuth2登入設定
                .oauth2Login(oauth2 -> oauth2
                        // 自訂redirect-uri base
                        .redirectionEndpoint(redir -> redir
                                .baseUri("/auth/oauth2/callback/*") // 取代預設/login/oauth2/code/*
                        )
                        // 當OAuth2 provider已經完成授權，呼叫customOAuth2UserService.loadUser(request)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler))

                // 在UsernamePasswordAuthenticationFilter之前加入JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 建立完整的filter chain
        // Spring Security自動註冊到filter排程中
        return http.build();
    }

    // 跨域設定
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}