package com.eeit205team5.rentalmanagement.property.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.eeit205team5.rentalmanagement.property.dto.PropertySearchRequest;
import com.eeit205team5.rentalmanagement.property.entity.Property;
import com.eeit205team5.rentalmanagement.property.service.PropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/properties")
    public ApiResponse<List<Property>> searchProperties(@RequestBody PropertySearchRequest request) {
        Page<Property> properties = propertyService.searchProperties(request);
        if (properties.isEmpty()) {
            return ApiResponse.fail("查無資料");
        }
        return ApiResponse.successPage(properties, "查詢成功");
    }
}
