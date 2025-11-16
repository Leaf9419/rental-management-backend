package com.eeit205team5.rentalmanagement.property.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import com.eeit205team5.rentalmanagement.property.constant.BuildingType;
import com.eeit205team5.rentalmanagement.property.constant.PreferredTenantGender;
import com.eeit205team5.rentalmanagement.property.constant.PropertyType;
import com.eeit205team5.rentalmanagement.property.constant.PublishStatus;
import com.eeit205team5.rentalmanagement.property.constant.RentalStatus;
import com.eeit205team5.rentalmanagement.property.constant.ReviewStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.DELETED)
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "property_code", insertable = false, updatable = false)
    private String propertyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    // 聯絡人
    @Column(name = "contact_role")
    private String contactRole;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_line")
    private String contactLine;

    // 地址
    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    // geography_point 是計算欄位，通常不在 Entity 中映射
    // 如果需要可以使用 @Formula 或在查詢時計算

    // 建物
    @Column(name = "building_name")
    private String buildingName;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_type")
    private BuildingType buildingType;

    @Column(name = "total_floors")
    private Integer totalFloors;

    @Column(name = "has_elevator")
    private Boolean hasElevator;

    // 格局
    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "living_rooms")
    private Integer livingRooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "balconies")
    private Integer balconies;

    @Column(name = "parking_spaces")
    private Integer parkingSpaces;

    @Column(name = "usable_area")
    private BigDecimal usableArea;

    // 租客偏好
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_tenant_gender")
    private PreferredTenantGender preferredTenantGender;

    // 設備與設施
    @Column(name = "has_washing_machine")
    private Boolean hasWashingMachine;

    @Column(name = "has_refrigerator")
    private Boolean hasRefrigerator;

    @Column(name = "has_air_conditioner")
    private Boolean hasAirConditioner;

    @Column(name = "has_water_heater")
    private Boolean hasWaterHeater;

    @Column(name = "has_television")
    private Boolean hasTelevision;

    @Column(name = "has_bed")
    private Boolean hasBed;

    @Column(name = "has_wardrobe")
    private Boolean hasWardrobe;

    @Column(name = "has_desk")
    private Boolean hasDesk;

    @Column(name = "has_internet")
    private Boolean hasInternet;

    @Column(name = "has_natural_gas")
    private Boolean hasNaturalGas;

    @Column(name = "has_fire_extinguisher")
    private Boolean hasFireExtinguisher;

    @Column(name = "has_smoke_detector")
    private Boolean hasSmokeDetector;

    // 開伙 & 養寵物
    @Column(name = "allows_cooking")
    private Boolean allowsCooking;

    @Column(name = "allows_pets")
    private Boolean allowsPets;

    // 租金
    @Column(name = "monthly_rent")
    private Integer monthlyRent;

    @Column(name = "deposit_months")
    private BigDecimal depositMonths;

    @Column(name = "management_fee")
    private Integer managementFee;

    @Column(name = "utility_fee_included")
    private Boolean utilityFeeIncluded;

    // 租期
    @Column(name = "min_lease_months")
    private Integer minLeaseMonths;

    @Column(name = "available_immediately")
    private Boolean availableImmediately;

    @Column(name = "available_from_date")
    private LocalDateTime availableFromDate;

    // 擁有權證
    @Column(name = "has_ownership_certificate")
    private Boolean hasOwnershipCertificate;

    @Column(name = "certificate_image_url")
    private String certificateImageUrl;

    // 狀態
    @Enumerated(EnumType.STRING)
    @Column(name = "publish_status")
    private PublishStatus publishStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status")
    private ReviewStatus reviewStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    // 業務時間戳 format: yyyy-MM-dd HH:mm:ss
    @Column(name = "first_published_at")
    private LocalDateTime firstPublishedAt;

    @Column(name = "last_published_at")
    private LocalDateTime lastPublishedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    // 審計時間戳 format: yyyy-MM-dd HH:mm:ss
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
