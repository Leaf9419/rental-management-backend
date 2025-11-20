package com.eeit205team5.rentalmanagement.property.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortOrder {
    ASC("asc", "升序"),
    DESC("desc", "降序");

    private final String value;
    private final String displayName;
}