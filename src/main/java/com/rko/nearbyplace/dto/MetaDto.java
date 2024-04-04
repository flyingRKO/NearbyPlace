package com.rko.nearbyplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MetaDto(
        @JsonProperty("total_count")
        Integer totalCount
) {
}
