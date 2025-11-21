package com.eeit205team5.rentalmanagement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.security.entity.User;
import com.eeit205team5.rentalmanagement.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// UserDetailsService:用來讓Spring Security取得UserDetails的標準介面
// 根據使用者識別資訊(username)載入使用者的安全認證資訊
/*
當Spring Security看到AuthenticationProvider需要UserDetailsService時，它會找@Bean UserDetailsService
然後會找到這個類別，因為有implement UserDetailsService
就能使用這裡覆寫的方法，而不是原本UserDetailsService的方法，回傳值就能是自己寫的UserPrincipal物件
*/
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    /**
     * 根據userId載入使用者(JWT驗證時用，在JWT filter裡呼叫)
     * 
     * @param userId
     * @return UserPrincipal物件
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId不可為null");
        }

        User user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("找不到使用者ID: " + userId));

        return UserPrincipal.create(user);
    }

    /**
     * 根據email載入使用者
     * 
     * @param email
     * @return UserPrincipal物件
     */
    // 注意:UserDetailsService介面只有loadUserByUsername()
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("找不到使用者: " + email));

        return UserPrincipal.create(user);
    }
}