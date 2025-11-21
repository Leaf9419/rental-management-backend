package com.eeit205team5.rentalmanagement.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eeit205team5.rentalmanagement.property.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>,
    JpaSpecificationExecutor<Property> {
}