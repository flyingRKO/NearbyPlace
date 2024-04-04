package com.rko.nearbyplace.dto;

import com.rko.nearbyplace.enumeration.Category;

public record InputDto(
        Category category,
        String address
) {
}
