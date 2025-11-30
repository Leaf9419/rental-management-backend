package com.eeit205team5.rentalmanagement.security.oauth2;

import java.util.Map;

// 把attributes轉換成統一格式:id、name、email、imageUrl
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    // 父類沒有無參數建構子，必須明確呼叫super(...)
    // 否則編譯器會嘗試自動插入super()，會編譯錯誤
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    
    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }
    
    @Override
    public String getName() {
        return (String) getAttributes().get("name");
    }
    
    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) getAttributes().get("picture");
    }
}
