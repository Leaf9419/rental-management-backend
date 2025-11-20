package com.eeit205team5.rentalmanagement.property.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.eeit205team5.rentalmanagement.property.constant.SortBy;
import com.eeit205team5.rentalmanagement.property.constant.SortOrder;
import com.eeit205team5.rentalmanagement.property.dto.PropertySearchRequest;
import com.eeit205team5.rentalmanagement.property.entity.Property;
import com.eeit205team5.rentalmanagement.property.repository.PropertyRepository;
import com.eeit205team5.rentalmanagement.property.repository.PropertySpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

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
                PropertySpecification.byPreferredTenantGender(request.getPreferredTenantGender()));

        // 3. 執行查詢
        return propertyRepository.findAll(spec, pageable);
    }
}