package com.eeit205team5.rentalmanagement.security.oauth2;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED) // 只能由子類別讀取attributes
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 只能由子類別建立
public abstract class OAuth2UserInfo {
    // final:變數引用不能被重新指派
    private final Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}