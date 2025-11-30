package com.eeit205team5.rentalmanagement.security.oauth2;

import java.util.Map;

import com.eeit205team5.rentalmanagement.common.exception.BusinessException;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> new GoogleOAuth2UserInfo(attributes);
            case "line" -> new LineOAuth2UserInfo(attributes);
            default -> throw new BusinessException("不支援的OAuth2 provider: " + registrationId);
        };
    }
}