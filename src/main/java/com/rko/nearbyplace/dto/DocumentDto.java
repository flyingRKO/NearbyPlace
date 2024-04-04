package com.rko.nearbyplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DocumentDto(
        @JsonProperty("place_name")
        String placeName,
        @JsonProperty("address_name")
        String addressName,
        @JsonProperty("x")
        double longitude,
        @JsonProperty("y")
        double latitude,
        @JsonProperty("distance")
        int distance
) {
}
