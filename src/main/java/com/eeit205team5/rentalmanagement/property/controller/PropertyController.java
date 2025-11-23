package com.eeit205team5.rentalmanagement.property.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.eeit205team5.rentalmanagement.property.dto.PropertyCreateRequest;
import com.eeit205team5.rentalmanagement.property.dto.PropertySearchRequest;
import com.eeit205team5.rentalmanagement.property.dto.PropertyUpdateRequest;
import com.eeit205team5.rentalmanagement.property.entity.Property;
import com.eeit205team5.rentalmanagement.property.service.PropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Property> createProperty(@RequestBody PropertyCreateRequest request) {
        Property property = propertyService.createProperty(request);
        return ApiResponse.success(property, 1L, "房源新增成功");
    }

    @PutMapping("/{propertyId}")
    public ApiResponse<Property> updateProperty(@PathVariable Long propertyId,
            @RequestBody PropertyUpdateRequest request) {
        Property property = propertyService.updateProperty(propertyId, request);
        return ApiResponse.success(property, 1L, "房源更新成功");
    }

    @DeleteMapping("/{propertyId}")
    public ApiResponse<Property> deleteProperty(@PathVariable Long propertyId) {
        Property property = propertyService.deleteProperty(propertyId);
        return ApiResponse.success(property, 1L, "房源刪除成功");
    }

    @PostMapping("/search")
    public ApiResponse<List<Property>> searchProperties(@RequestBody PropertySearchRequest request) {
        Page<Property> properties = propertyService.searchProperties(request);
        if (properties.isEmpty()) {
            return ApiResponse.fail("查無資料");
        }
        return ApiResponse.successPage(properties, "查詢成功");
    }

}
