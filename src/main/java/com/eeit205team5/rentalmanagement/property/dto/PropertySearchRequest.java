package com.eeit205team5.rentalmanagement.property.dto;

import java.math.BigDecimal;

import com.eeit205team5.rentalmanagement.property.constant.BuildingType;
import com.eeit205team5.rentalmanagement.property.constant.PreferredTenantGender;
import com.eeit205team5.rentalmanagement.property.constant.PropertyType;
import com.eeit205team5.rentalmanagement.property.constant.SortBy;
import com.eeit205team5.rentalmanagement.property.constant.SortOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchRequest {
    // 關鍵字
    private String keyword;
    // 房源類型
    private PropertyType propertyType;
    // 建築類型
    private BuildingType buildingType;
    // 地區
    private String city;
    private String district;
    // 價格
    private BigDecimal minRent;
    private BigDecimal maxRent;
    // 面積
    private BigDecimal minArea;
    private BigDecimal maxArea;
    // 房數
    private Integer bedrooms;
    // 樓層
    private Integer minFloor;
    private Integer maxFloor;
    // 設施
    private Long facilitiesBitmask;
    // 特色
    private Boolean hasElevator;
    private Boolean hasParkingSpace;
    private Boolean hasBalcony;
    private Boolean allowsCooking;
    private Boolean allowsPets;
    // 希望租客性別
    private PreferredTenantGender preferredTenantGender;
    // 分頁與排序
    private SortBy sortBy;
    private SortOrder sortOrder;
    private Integer page;
    private Integer size;
}
