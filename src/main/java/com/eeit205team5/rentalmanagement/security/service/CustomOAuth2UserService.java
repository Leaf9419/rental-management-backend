package com.eeit205team5.rentalmanagement.security.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eeit205team5.rentalmanagement.common.exception.BusinessException;
import com.eeit205team5.rentalmanagement.security.basic.CustomUserDetails;
import com.eeit205team5.rentalmanagement.security.entity.User;
import com.eeit205team5.rentalmanagement.security.oauth2.OAuth2UserInfo;
import com.eeit205team5.rentalmanagement.security.oauth2.OAuth2UserInfoFactory;
import com.eeit205team5.rentalmanagement.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    // 把不同provider回傳資料轉換成同一種格式，建立新帳號或更新帳號
    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        // 取得provider名稱(從client-id)
        String registrationId = request.getClientRegistration().getRegistrationId();

        // 從OAuth2User取得使用者資訊
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
                oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getId())) {
            throw new BusinessException("無法從OAuth2 provider取得使用者ID");
        }

        // 檢查是否已存在該第三方帳號
        Optional<User> userOptional = userRepository.findByProviderAndProviderId(registrationId,
                oAuth2UserInfo.getId());

        if (userOptional.isPresent()) {
            // 已存在同一個provider帳號，更新使用者資訊
            return CustomUserDetails.create(updateExistingUser(userOptional.get(), oAuth2UserInfo),
                    oAuth2User.getAttributes());
        }

        // 該provider帳號不存在，檢查Email是否已存在
        if (oAuth2UserInfo.getEmail() != null) {
            Optional<User> existingEmailUser = userRepository.findByEmail(oAuth2UserInfo.getEmail());

            if (existingEmailUser.isPresent()) {
                User user = existingEmailUser.get();

                // 自動綁定provider
                user.setProvider(registrationId);
                user.setProviderId(oAuth2UserInfo.getId());
                user.setLastLoginAt(Instant.now());
                user.setUpdatedAt(Instant.now());

                if (!user.getEmailVerified()) {
                    user.setEmailVerified(true);
                }

                log.info("Email已存在，自動綁定 {} 帳號: userId={}", registrationId, user.getUserId());

                return CustomUserDetails.create(userRepository.save(user), oAuth2User.getAttributes());
            }
        }

        // Email不存在或未提供，註冊新使用者
        User newUser = registerNewUser(registrationId, oAuth2UserInfo);

        return CustomUserDetails.create(newUser, oAuth2User.getAttributes());
    }

    // 註冊新的OAuth2使用者
    private User registerNewUser(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setEmail(oAuth2UserInfo.getEmail());
        user.setPhone(null);
        user.setRole("tenant"); // 預設角色為tenant
        user.setStatus("active");
        user.setEmailVerified(oAuth2UserInfo.getEmail() != null); // 第三方已驗證Email
        user.setPhoneVerified(false);
        user.setProvider(provider);
        user.setProviderId(oAuth2UserInfo.getId());

        log.info("OAuth2使用者註冊成功: provider={}, providerId={}", provider, oAuth2UserInfo.getId());

        return userRepository.save(user);
    }

    // 更新現有OAuth2使用者(Email)
    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        // 更新Email
        if (oAuth2UserInfo.getEmail() != null && !oAuth2UserInfo.getEmail().equals(user.getEmail())) {
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setEmailVerified(true);
        }

        user.setLastLoginAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    /**
     * 在OAuth2取得使用者資訊後做註冊或資料同步(SecurityConfig設定後自動呼叫)
     * 
     * @param userRequest
     * @return OAuth2User物件
     */
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 用access_token在user-info-uri取得使用者資料，包成OAuth2User(attributes)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        /*
         * if (userRequest.getClientRegistration().getRegistrationId().equals("line")) {
         * // 如果是OIDC provider
         * OidcUser oidcUser = (OidcUser) oAuth2User;
         * OidcIdToken idToken = oidcUser.getIdToken(); // 取得ID token，Spring會自動驗證簽名
         * OidcUserInfo userInfo = oidcUser.getUserInfo(); // 可選，profile資訊
         * return processOAuth2User(userRequest, oidcUser);
         * // 驗證idToken簽名、iss、aud、exp，然後再映射到User
         * }
         */

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (BusinessException e) { // processOAuth2User()可能會丟出BusinessException
            throw e;
            /*
             * 會把processOAuth2User()的BusinessException原封不動重新往上丟
             * 在Spring Security filter chain裡發生的，不是進入MVC Controller的流程
             * 不會被ControllerAdvice的全域@ExceptionHandler捕捉到
             * Spring
             * Security會把它包成OAuth2AuthenticationException或InternalAuthenticationServiceException
             * 由OAuth2 failure handler處理
             */
        } catch (Exception e) {
            log.error("OAuth2登入未知錯誤", e);
            // log.error("錯誤: {}", e); // 只印e.toString()
            // log.error("錯誤: " + e); // 只印e.toString()

            throw new InternalAuthenticationServiceException("OAuth2登入失敗，請稍後再試");
        }
    }
}