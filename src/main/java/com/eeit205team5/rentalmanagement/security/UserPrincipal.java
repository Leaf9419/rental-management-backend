package com.eeit205team5.rentalmanagement.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eeit205team5.rentalmanagement.security.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 安全模型
// UserDetails:代表系統中的使用者的核心介面(使用者身分模型)
// Spring Security的Authentication必須使用UserDetails，而不是User Entity
// User entity會被轉換成UserPrincipal，讓Spring Security內部可以統一管理使用者登入狀態
@Getter
@Setter
@AllArgsConstructor // create()
public class UserPrincipal implements UserDetails {
    private Long userId;
    private String email;
    private String password;
    private String role;
    private String status;
    // ?可以為SimpleGrantedAuthority、OAuth2GrantedAuthority、JwtGrantedAuthority、任何自己實作的權限類別，能適應不同安全框架
    private Collection<? extends GrantedAuthority> authorities; // 權限格式ROLE_XXXX

    // 靜態工廠方法
    // 將資料庫模型傳換成Security認證模型
    // 將資料庫模型與Security模型分離，好維護
    // Spring Security要求:角色格式必須是ROLE_XXX、用SimpleGrantedAuthority包裝才能拿去驗證
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = List.of(
                // Spring Security檢查權限用@PreAuthorize("hasRole('XXXX')")
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        return new UserPrincipal(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getStatus(),
                authorities);
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
     * 是的話就呼叫UserPrincipal.getUsername()，回傳email
     */
    @Override
    public String getUsername() {
        return email; // 使用email作為username
    }

    // ===== 以下在UserDetails為default method，預設都會回傳true =====
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
}

// Spring Security只要求UserDetails必須有:username(帳號)、password(驗證)、authorities(權限控管)