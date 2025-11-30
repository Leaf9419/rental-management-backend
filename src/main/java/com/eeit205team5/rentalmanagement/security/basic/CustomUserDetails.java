package com.eeit205team5.rentalmanagement.security.basic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.eeit205team5.rentalmanagement.security.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 安全模型
// UserDetails:代表系統中的使用者的核心介面(使用者身分模型)
// Spring Security的Authentication必須使用UserDetails，而不是User Entity
// User entity會被轉換成CustomUserDetails，讓Spring Security內部可以統一管理使用者登入狀態
// OAuth2User:就是用來讓Spring Security能統一管理第三方登入使用者資料的介面。和UserDetails一樣，是Spring Security處理登入狀態與授權的核心
@Getter
@Setter
@AllArgsConstructor // create()
public class CustomUserDetails implements UserDetails, OAuth2User {
    private Long userId;
    private String email;
    private String password;
    private String role;
    private String status;
    // ?可以為SimpleGrantedAuthority、OAuth2GrantedAuthority、JwtGrantedAuthority、任何自己實作的權限類別，能適應不同安全框架
    private Collection<? extends GrantedAuthority> authorities; // 權限格式ROLE_XXXX
    private Map<String, Object> attributes; // OAuth2

    // 利用User entity建立CustomUserDetails(一般登入用)
    // 靜態工廠方法
    // 將資料庫模型傳換成Security認證模型
    // 將資料庫模型與Security模型分離，好維護
    // Spring Security要求:角色格式必須是ROLE_XXX、用SimpleGrantedAuthority包裝才能拿去驗證
    public static CustomUserDetails create(User user) {
        List<GrantedAuthority> authorities = List.of(
                // Spring Security檢查權限用@PreAuthorize("hasRole('XXXX')")
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getStatus(),
                authorities,
                null); // 本地登入不需要attributes
    }

    // 利用User entity建立CustomUserDetails(OAuth2登入用)
    public static CustomUserDetails create(User user, Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = create(user);
        customUserDetails.setAttributes(attributes);

        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /*
     * 呼叫AbstractAuthenticationToken的authentication.getName()
     * getName()內部會檢查principal是否是UserDetails
     * 是的話就呼叫CustomUserDetails.getUsername()，回傳email
     */
    @Override
    public String getUsername() {
        return email; // 使用email作為username
    }

    // ===== 以下在UserDetails為default method，預設都會回傳true =====
    // ===== 在驗證使用者時會自動呼叫 =====
    // 帳號永遠不會過期
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // true
    }

    // 被封鎖(suspended)就不能登入
    @Override
    public boolean isAccountNonLocked() {
        return !"suspended".equals(status);
    }

    // 密碼永遠不會過期
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // true
    }

    // 帳號必須是active才能登入
    // inactive(未啟用、尚未驗證、暫時停用)、suspended(停權)不能登入
    @Override
    public boolean isEnabled() {
        return "active".equals(status);
    }

    // ===== OAuth2User介面方法 =====
    // OAuth2使用者名稱，通常是唯一ID
    @Override
    public String getName() {
        return String.valueOf(userId); // 字串?
    }

    /*
     * 不同OAuth2 provider回傳的使用者資訊格式可能不一樣
     * - Google:sub、name、email、picture
     * - LINE:userId、displayName、email、pictureUrl
     * 把這些資料統一封裝成一個Map<String, Object>，方便後續處理
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}

// Spring Security只要求UserDetails必須有:username(帳號)、password(驗證)、authorities(權限控管)