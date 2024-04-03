package com.rko.nearbyplace.service;

import com.rko.nearbyplace.enumeration.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("address : {}, uri : {}", address, uri);

        return uri;
    }

    public URI buildUriByCategorySearch(double lat, double lon, double radius, Category category) {

//        double meterRadius = radius * 1000;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code", category);
        uriBuilder.queryParam("x", lon);
        uriBuilder.queryParam("y", lat);
        uriBuilder.queryParam("radius", radius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();
        log.info("uri : {}", uri);

        return uri;
    }
}
