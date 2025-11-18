package com.eeit205team5.rentalmanagement.property.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.eeit205team5.rentalmanagement.property.entity.Property;
import com.eeit205team5.rentalmanagement.property.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Page<Property> findProperties(PageRequest<PropertyQueryFilter> request) {
        PropertyQueryFilter filter = request.getFilter();

        // 建立 Specification
        Specification<Property> spec = PropertySpecifications.fromFilter(filter);

        // 建立 Spring Data Pageable
        Sort sort = Sort.by(
                request.getDir() ? Sort.Direction.DESC : Sort.Direction.ASC,
                request.getOrder() == null ? "propertyId" : request.getOrder());

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                request.getStart() / request.getRows(), // 頁碼
                request.getRows(), // 每頁筆數
                sort);

        // 🎉 一行搞定！
        return propertyRepository.findAll(spec, pageable);
    }

    public long countProperties(PropertyQueryFilter filter) {
        Specification<Property> spec = PropertySpecifications.fromFilter(filter);
        return propertyRepository.count(spec);
    }
}
