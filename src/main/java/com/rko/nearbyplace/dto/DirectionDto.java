package com.rko.nearbyplace.dto;

import lombok.Builder;

@Builder
public record DirectionDto(
        String inputAddress,
        double inputLatitude,
        double inputLongitude,

        String targetPlaceName,
        String targetAddress,
        double targetLatitude,
        double targetLongitude,
        int distance
) {
}
