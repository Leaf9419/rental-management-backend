package com.eeit205team5.rentalmanagement.property.constant;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum SortBy {
    RENT("rent", "租金"),
    AREA("area", "面積"),
    BEDROOMS("bedrooms", "房間數"),
    FLOOR("floor", "樓層"),
    CREATED_AT("createdAt", "建立時間"),
    UPDATED_AT("updatedAt", "更新時間"),
    VIEW_COUNT("viewCount", "瀏覽次數");

    private final String value;
    private final String displayName;
}