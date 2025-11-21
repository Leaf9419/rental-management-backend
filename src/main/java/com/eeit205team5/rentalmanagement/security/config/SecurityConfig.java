package com.eeit205team5.rentalmanagement.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eeit205team5.rentalmanagement.PermitUrlJohn;
import com.eeit205team5.rentalmanagement.PermitUrlLeaf;
import com.eeit205team5.rentalmanagement.PermitUrlNa;
import com.eeit205team5.rentalmanagement.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter filter;

    // 提供全域PasswordEncoder bean，加密和比對密碼
    // 這裡指定使用BCryptPasswordEncoder實作類別
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 用於自動注入PasswordEncoder passwordEncoder
    }

    // 認證提供者(帳密登入用)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // security filter chain設定
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 停用CSRF(因為使用JWT)
                .csrf(csrf -> csrf.disable())

                // Session管理:無狀態
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 授權設定
                .authorizeHttpRequests(auth -> auth
                        // 公開的API(不需要認證)
                        .requestMatchers(
                                "/auth/register",
                                "/auth/login")
                        .permitAll()
                        .requestMatchers(PermitUrlLeaf.URLS).permitAll()
                        .requestMatchers(PermitUrlNa.URLS).permitAll()
                        .requestMatchers(PermitUrlJohn.URLS).permitAll()
                        // 其他所有請求都需要認證
                        .anyRequest().authenticated())

                // 在UsernamePasswordAuthenticationFilter之前加入JWT filter
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}