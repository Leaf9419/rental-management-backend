package com.eeit205team5.rentalmanagement.security.oauth2;

import java.util.Map;

// 把attributes轉換成統一格式:id、name、email、imageUrl
public class LineOAuth2UserInfo extends OAuth2UserInfo{
    public LineOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    
    @Override
    public String getId() {
        return (String) getAttributes().get("userId");
        // return (String) getAttributes().get("sub");
    }

    @Override
    public String getName() {
        return (String) getAttributes().get("displayName");
    }

    @Override
    public String getEmail() {
        // LINE的email需要額外申請權限，可能為null
        return (String) getAttributes().get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) getAttributes().get("pictureUrl");
    }
}