package com.eeit205team5.rentalmanagement.security.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.common.exception.BusinessException;
import com.eeit205team5.rentalmanagement.security.JwtTokenProvider;
import com.eeit205team5.rentalmanagement.security.dto.request.LoginRequest;
import com.eeit205team5.rentalmanagement.security.dto.request.RegisterRequest;
import com.eeit205team5.rentalmanagement.security.dto.response.AuthResponse;
import com.eeit205team5.rentalmanagement.security.entity.User;
import com.eeit205team5.rentalmanagement.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder; // 在SecurityConfig配置@Bean，BCryptPasswordEncoder物件自動注入passwordEncoder
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager; // 不能自己new，透過SecurityConfig自動注入

    /**
     * 使用者註冊
     * 
     * @param request
     * @return AuthResponse物件，或驗證失敗丟Exception(ControllerAdvice捕捉)
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 檢查Email是否已存在
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessException("此Email已被註冊");
        }

        // 檢查手機號碼是否已存在
        if (request.getPhone() != null && repository.existsByPhone(request.getPhone())) {
            throw new BusinessException("此手機號碼已被註冊");
        }

        // 驗證角色
        // 避免惡意傳入admin
        if (!request.getRole().equals("tenant") && !request.getRole().equals("landlord")) {
            throw new BusinessException("角色只能是tenant或landlord");
        }

        // 建立新使用者
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt加密
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setStatus("active");
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setProvider(null); // 本地註冊
        user.setProviderId(null);

        // 存入資料庫
        User saved = repository.save(user);

        // 自動登入，生成JWT
        /*
         * 建立UsernamePasswordAuthenticationToken物件
         * 並交給AuthenticationManager物件進行認證
         * AuthenticationManager物件會自動做以下流程:
         * 1.UserDetailsService.loadUserByUsername(email)
         * 2.查出該使用者(UserPrincipal物件)
         * 3.BCrypt比對密碼
         * 4.產生Authentication物件，代表認證成功
         */
        // 如果密碼錯誤或帳號不存在，authenticate()會丟出BadCredentialsException或UsernameNotFoundException
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // 把登入狀態放進SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成JWT
        String jwt = jwtTokenProvider.generateToken(authentication);

        // 回傳結果
        return AuthResponse.builder()
                .accessToken(jwt)
                .userId(saved.getUserId())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

    /**
     * 使用者登入
     * 
     * @param request
     * @return AuthResponse物件，或驗證失敗丟Exception
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        // 驗證使用者帳密
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        // 更新最後登入時間
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("找不到使用者"));

        user.setLastLoginAt(Instant.now());

        return AuthResponse.builder()
                .accessToken(jwt)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /**
     * 獲取當前登入使用者資訊
     * 
     * @return User物件，或丟Exception
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("未登入");
        }

        /*
         * getName():預設實作類別是AbstractAuthenticationToken
         * 
         * @Override
         * public String getName() {
         * return (this.principal instanceof UserDetails) ? ((UserDetails)
         * this.principal).getUsername()
         * : this.principal.toString();
         * }
         * 
         * UserPrincipal內部:
         * 
         * @Override
         * public String getUsername() {
         * return this.email; // 回傳的是email
         * }
         */
        String email = authentication.getName();

        return repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("找不到使用者"));
    }
}

/*
 * 帳密登入步驟:
 * new UsernamePasswordAuthenticationToken(
 * request.getEmail(),
 * request.getPassword()));
 * 一開始給它兩個參數:principal(使用者的識別，email)、credentials(密碼)
 * 這個token物件此時就像是一個待驗證的登入請求:
 * UsernamePasswordAuthenticationToken {
 * principal = "user@example.com"
 * credentials = "123456"
 * authorities = null (因為還沒驗證)
 * authenticated = false
 * }
 * 
 * 之後token被放進authenticate()
 * authenticationManager.authenticate(token);
 * AuthenticationManager會做:
 * 1.觸發DaoAuthenticationProvider.authenticate()
 * 2.呼叫UserDetailsService.loadUserByUsername(email)
 * 3.找到對應的UserDetails物件(包含加密後的密碼)
 * 4.用PasswordEncoder物件比對密碼
 * 5.驗證成功，回傳一個authenticated = true的token
 * 成功後的token會變成:
 * UsernamePasswordAuthenticationToken {
 * principal = UserPrincipal (對應的UserDetails物件)
 * credentials = null (為安全起見會清掉)
 * authorities = [ROLE_TENANT]/[ROLE_LANDLORD] (userPrincipal.getAuthorities())
 * authenticated = true
 * }
 * 最後放進SecurityContext
 * SecurityContextHolder.getContext().setAuthentication(authentication);
 * 
 * Spring Security不管是登錄、JWT、自訂filter，一律都使用Authentication 物件
 * UsernamePasswordAuthenticationToken也可以用在JWT
 * JWT驗證不是用密碼登入，但流程一樣:
 * 1.從JWT拿到userId
 * 2.在filter裡手動呼叫UserDetailsService.loadUserById()找資料，得到UserDetails
 * 3.建一個已驗證的token:
 * UsernamePasswordAuthenticationToken {
 * principal = UserPrincipal (對應的UserDetails物件)
 * credentials = null (為安全起見會清掉)
 * authorities = [ROLE_TENANT]/[ROLE_LANDLORD]
 * authenticated = true
 * }
 * 最後放進SecurityContext
 */