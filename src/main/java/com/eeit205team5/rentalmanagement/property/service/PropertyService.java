package com.eeit205team5.rentalmanagement.property.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.property.constant.PropertyFacility;
import com.eeit205team5.rentalmanagement.property.constant.PublishStatus;
import com.eeit205team5.rentalmanagement.property.constant.SortBy;
import com.eeit205team5.rentalmanagement.property.constant.SortOrder;
import com.eeit205team5.rentalmanagement.property.dto.PropertyCreateRequest;
import com.eeit205team5.rentalmanagement.property.dto.PropertySearchRequest;
import com.eeit205team5.rentalmanagement.property.entity.Property;
import com.eeit205team5.rentalmanagement.property.repository.PropertyRepository;
import com.eeit205team5.rentalmanagement.property.repository.PropertySpecification;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyService {

        private final PropertyRepository propertyRepository;
        private final EntityManager entityManager;

        public Page<Property> searchProperties(PropertySearchRequest request) {
                // 1. 建立分頁請求
                int page = request.getPage() != null ? request.getPage() : 0;
                int size = request.getSize() != null ? request.getSize() : 10;
                SortBy sortBy = request.getSortBy() != null ? request.getSortBy() : SortBy.CREATED_AT;
                SortOrder sortOrder = request.getSortOrder() != null ? request.getSortOrder() : SortOrder.DESC;
                Sort sort = Sort.by(
                                sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
                                sortBy.getValue());
                Pageable pageable = PageRequest.of(page, size, sort);

                // 2. 建立查詢規格
                Specification<Property> spec = Specification.allOf(
                                PropertySpecification.withKeyword(request.getKeyword()),
                                PropertySpecification.byPropertyType(request.getPropertyType()),
                                PropertySpecification.byBuildingType(request.getBuildingType()),
                                PropertySpecification.byLocation(request.getCity(), request.getDistrict()),
                                PropertySpecification.inRentRange(request.getMinRent(), request.getMaxRent()),
                                PropertySpecification.inAreaRange(request.getMinArea(), request.getMaxArea()),
                                PropertySpecification.byBedrooms(request.getBedrooms()),
                                PropertySpecification.inFloorRange(request.getMinFloor(), request.getMaxFloor()),
                                PropertySpecification.hasFacilities(request.getFacilitiesBitmask()),
                                PropertySpecification.hasFeatures(request.getHasElevator(),
                                                request.getHasParkingSpace(),
                                                request.getHasBalcony(), request.getAllowsCooking(),
                                                request.getAllowsPets()),
                                PropertySpecification.byPreferredTenantGender(request.getPreferredTenantGender()),
                                PropertySpecification.byPublishStatus(PublishStatus.ACTIVE));

                // 3. 執行查詢
                return propertyRepository.findAll(spec, pageable);
        }

        public Property createProperty(PropertyCreateRequest request) {
                Property property = Property.builder()
                                .propertyType(request.getPropertyType())
                                .title(request.getTitle())
                                .description(request.getDescription())
                                // 聯絡人 5
                                .contactRole(request.getContactRole())
                                .contactName(request.getContactName())
                                .contactPhone(request.getContactPhone())
                                .contactEmail(request.getContactEmail())
                                .contactLine(request.getContactLine())
                                // 地址 6
                                .city(request.getCity())
                                .district(request.getDistrict())
                                .streetAddress(request.getStreetAddress())
                                .floor(request.getFloor())
                                .latitude(request.getLatitude())
                                .longitude(request.getLongitude())
                                // 建物 4
                                .buildingName(request.getBuildingName())
                                .buildingType(request.getBuildingType())
                                .totalFloors(request.getTotalFloors())
                                .hasElevator(request.getHasElevator())
                                // 格局 6
                                .bedrooms(request.getBedrooms())
                                .livingRooms(request.getLivingRooms())
                                .bathrooms(request.getBathrooms())
                                .balconies(request.getBalconies())
                                .parkingSpaces(request.getParkingSpaces())
                                .usableArea(request.getUsableArea())
                                // 租客偏好
                                .preferredTenantGender(request.getPreferredTenantGender())
                                // 設備與設施
                                .propertyFacilities(PropertyFacility.encode(request.getFacilities()))
                                // 開伙 & 養寵物
                                .allowsCooking(request.getAllowsCooking())
                                .allowsPets(request.getAllowsPets())
                                // 租金 4
                                .monthlyRent(request.getMonthlyRent())
                                .depositMonths(request.getDepositMonths())
                                .managementFee(request.getManagementFee())
                                .utilityFeeIncluded(request.getUtilityFeeIncluded())
                                // 租期
                                .minLeaseMonths(request.getMinLeaseMonths())
                                .availableFromDate(request.getAvailableFromDate())
                                // 擁有權證
                                .hasOwnershipCertificate(request.getHasOwnershipCertificate())
                                .certificateImageUrl(request.getCertificateImageUrl())
                                // build
                                .build();
                // 儲存
                propertyRepository.save(property);
                // 更新
                entityManager.refresh(property);
                return property;
        }
}