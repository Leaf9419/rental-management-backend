package com.eeit205team5.rentalmanagement.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// OncePerRequestFilter:抽象過濾器類別，保證每個HTTP請求只被過濾一次
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService service;

    // 從request header中取得JWT
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    // 每一次HTTP請求都會檢查JWT
    // 如果合法就把對應使用者資訊放進Spring Security的上下文
    // 讓後續程式認為這個請求已經登入，然後把請求交給下一個Filter或Controller處理
    // 不推薦加@NonNull，會和父類別不一致
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            // 驗證JWT
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);

                // Spring Security認證流程(不管是帳密登入或JWT)，永遠需要用UserDetails來建立 authentication(規範)
                // authentication必須有principal
                // principal型別必須是UserDetails
                // 而UserDetails只能透過UserDetailsService取得(標準化使用者資訊來源)
                UserDetails userDetails = service.loadUserById(userId);

                // 建立Spring Security認證物件
                // UsernamePasswordAuthenticationToken:最通用的Authentication實作
                // 封裝UserDetails + Authorities，代表這個請求已經認證成功
                // 所有的authentication不管來源，最後都要產生一個Authentication物件，並放入 SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, // principal // userId + email + password + role + status + authorities
                        null, // credentials(密碼) // JWT不需要，所以給null // 一般帳密登入就需要了
                        userDetails.getAuthorities()); // authorities

                // setDetails():把使用者IP、Session ID等request資訊放進Authentication物件，對日誌、安全判斷有用
                // 不是JWT驗證必須，但某些安全策略會需要，大多會加上它
                // buildDetails():產生WebAuthenticationDetails，放進request的資訊
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 把使用者資訊放入SecurityContext，後續Controller或Service可以使用
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("無法設置用戶認證: " + e);
        }

        // 把請求交給下一個filter，最後到達Servlet/Controller
        // 如果不呼叫doFilter()，請求就不會繼續往下走，Controller永遠不會收到請求
        filterChain.doFilter(request, response);
    }
}

// User entity轉換成UserPrincipal(實作UserDetails)，使用靜態工廠方法把資料庫User轉換成框架可用的模型
// CustomUserDetailsService(實作UserDetailsService)使用override的方法取得UserPrincipal，回傳UserDetails
// doFilterInternal內部就能透過CustomUserDetailsService取得UserDetails，用於UsernamePasswordAuthenticationToken

/*
 * 帳密登入
 * 1.Controller接收username、password
 * 2.AuthenticationManager建立UsernamePasswordAuthenticationToken
 * 3.驗證成功
 * 4.放入SecurityContext
 * 
 * JWT驗證
 * 1.filter拿到token
 * 2.解碼
 * 3.查UserDetails
 * 4.建立UsernamePasswordAuthenticationToken
 * 5.放入SecurityContext
 */