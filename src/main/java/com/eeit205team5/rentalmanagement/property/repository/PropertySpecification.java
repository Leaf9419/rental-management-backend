package com.eeit205team5.rentalmanagement.property.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.eeit205team5.rentalmanagement.property.constant.BuildingType;
import com.eeit205team5.rentalmanagement.property.constant.PreferredTenantGender;
import com.eeit205team5.rentalmanagement.property.constant.PropertyType;
import com.eeit205team5.rentalmanagement.property.constant.PublishStatus;
import com.eeit205team5.rentalmanagement.property.constant.RentalStatus;
import com.eeit205team5.rentalmanagement.property.constant.ReviewStatus;
import com.eeit205team5.rentalmanagement.property.entity.Property;

import jakarta.persistence.criteria.Predicate;

/*
 * by - 精確匹配條件：byPropertyType, byLocation
 * with - 模糊搜尋/包含：withKeyword
 * has - 包含特定功能：hasFacilities, hasElevator
 * in - 範圍查詢：inRentRange, inAreaRange
 * is - 狀態判斷：isAvailableNow (代替 availableNow)
 */
public class PropertySpecification {
    // 關鍵字
    public static Specification<Property> withKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), likePattern),
                    criteriaBuilder.like(root.get("buildingName"), likePattern),
                    criteriaBuilder.like(root.get("propertyCode"), likePattern));
        };
    }

    // 房源類型
    public static Specification<Property> byPropertyType(PropertyType propertyType) {
        return (root, query, criteriaBuilder) -> {
            if (propertyType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("propertyType"), propertyType);
        };
    }

    // 建築類型
    public static Specification<Property> byBuildingType(BuildingType buildingType) {
        return (root, query, criteriaBuilder) -> {
            if (buildingType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("buildingType"), buildingType);
        };
    }

    // 地區
    public static Specification<Property> byLocation(String city, String district) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }

            if (district != null && !district.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("district"), district));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 價格
    public static Specification<Property> inRentRange(BigDecimal minRent, BigDecimal maxRent) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minRent != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("monthlyRent"), minRent));
            }

            if (maxRent != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("monthlyRent"), maxRent));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 面積
    public static Specification<Property> inAreaRange(
            BigDecimal minArea, BigDecimal maxArea) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minArea != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("usableArea"), minArea));
            }

            if (maxArea != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("usableArea"), maxArea));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 房數
    public static Specification<Property> byBedrooms(Integer bedrooms) {
        return (root, query, criteriaBuilder) -> {
            if (bedrooms == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("bedrooms"), bedrooms);
        };
    }

    // 樓層
    public static Specification<Property> inFloorRange(Integer minFloor, Integer maxFloor) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minFloor != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("floor"), minFloor));
            }

            if (maxFloor != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("floor"), maxFloor));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 設施
    public static Specification<Property> hasFacilities(Long facilitiesBitmask) {
        return (root, query, criteriaBuilder) -> {
            if (facilitiesBitmask == null || facilitiesBitmask == 0) {
                return criteriaBuilder.conjunction();
            }

            // 使用 BITAND 檢查是否包含所有設施
            // (propertyFacilities & facilitiesBitmask) == facilitiesBitmask
            return criteriaBuilder.equal(
                    criteriaBuilder.function("&", Long.class,
                            root.get("propertyFacilities"),
                            criteriaBuilder.literal(facilitiesBitmask)),
                    facilitiesBitmask);
        };
    }

    // 特色
    public static Specification<Property> hasFeatures(
            Boolean hasElevator,
            Boolean hasParkingSpace,
            Boolean hasBalcony,
            Boolean allowsCooking,
            Boolean allowsPets) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (hasElevator != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("hasElevator"), hasElevator));
            }

            if (hasParkingSpace != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("parkingSpaces"), 1));
            }

            if (hasBalcony != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("balconies"), 1));
            }

            if (allowsCooking != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("allowsCooking"), allowsCooking));
            }

            if (allowsPets != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("allowsPets"), allowsPets));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 希望租客性別
    public static Specification<Property> byPreferredTenantGender(
            PreferredTenantGender preferredTenantGender) {
        return (root, query, criteriaBuilder) -> {
            if (preferredTenantGender == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("preferredTenantGender"), preferredTenantGender);
        };
    }

    // 發布狀態
    public static Specification<Property> byPublishStatus(PublishStatus publishStatus) {
        return (root, query, criteriaBuilder) -> {
            if (publishStatus == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("publishStatus"), publishStatus);
        };
    }

    // 出租狀態
    public static Specification<Property> byRentalStatus(RentalStatus rentalStatus) {
        return (root, query, criteriaBuilder) -> {
            if (rentalStatus == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("rentalStatus"), rentalStatus);
        };
    }

    // 審核狀態
    public static Specification<Property> byReviewStatus(ReviewStatus reviewStatus) {
        return (root, query, criteriaBuilder) -> {
            if (reviewStatus == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("reviewStatus"), reviewStatus);
        };
    }

    // 特定使用者的物件
    public static Specification<Property> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("userId"), userId);
        };
    }

    // 立即可入住
    public static Specification<Property> isAvailableNow() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("availableFromDate"),
                LocalDateTime.now());
    }

}
