package com.rko.nearbyplace.dto;

import lombok.Builder;

@Builder
public record OutputDto(
        String placeName,
        String placeAddress,
        String placeUrl,
        String roadViewUrl,
        String distance
) {
}
