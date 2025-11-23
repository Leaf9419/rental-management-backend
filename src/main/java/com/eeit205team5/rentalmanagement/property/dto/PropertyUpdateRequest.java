package com.eeit205team5.rentalmanagement.property.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.eeit205team5.rentalmanagement.property.constant.BuildingType;
import com.eeit205team5.rentalmanagement.property.constant.PreferredTenantGender;
import com.eeit205team5.rentalmanagement.property.constant.PropertyFacility;
import com.eeit205team5.rentalmanagement.property.constant.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyUpdateRequest {

    private PropertyType propertyType;
    private String title;
    private String description;

    // 聯絡人 5
    private String contactRole;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactLine;

    // 地址 6
    private String city;
    private String district;
    private String streetAddress;
    private Integer floor;
    private BigDecimal latitude;
    private BigDecimal longitude;

    // 建物 4
    private String buildingName;
    private BuildingType buildingType;
    private Integer totalFloors;
    private Boolean hasElevator;

    // 格局 6
    private Integer bedrooms;
    private Integer livingRooms;
    private Integer bathrooms;
    private Integer balconies;
    private Integer parkingSpaces;
    private BigDecimal usableArea;

    // 租客偏好
    private PreferredTenantGender preferredTenantGender;

    // 設備與設施
    private Set<PropertyFacility> facilities;

    // 開伙 & 養寵物
    private Boolean allowsCooking;
    private Boolean allowsPets;

    // 租金 4
    private BigDecimal monthlyRent;
    private BigDecimal depositMonths;
    private BigDecimal managementFee;
    private Boolean utilityFeeIncluded;

    // 租期
    private Integer minLeaseMonths;
    private LocalDateTime availableFromDate;

    // 擁有權證
    private Boolean hasOwnershipCertificate;
    private String certificateImageUrl;
}