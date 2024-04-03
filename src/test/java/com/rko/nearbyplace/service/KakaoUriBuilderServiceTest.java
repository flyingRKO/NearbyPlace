package com.rko.nearbyplace.service;

import com.rko.nearbyplace.enumeration.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class KakaoUriBuilderServiceTest {

    private KakaoUriBuilderService kakaoUriBuilderService;

    @BeforeEach
    void setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    @DisplayName("한글 파라미터의 경우 정상적으로 인코딩")
    @Test
    void testBuildUriByAddressSearchWithKoreanParameter() throws Exception {
        // Given
        String address = "대전 유성구";
        String charset = StandardCharsets.UTF_8.name();

        // When
        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);
        String decodedResult = URLDecoder.decode(uri.toString(), charset);

        // Then
        assertEquals("https://dapi.kakao.com/v2/local/search/address.json?query=대전 유성구", decodedResult);
    }

    @DisplayName("카테고리 검색을 위한 URI 구성 테스트")
    @Test
    void testBuildUriByCategorySearch() throws Exception {
        // Given
        double lat = 37.5665;
        double lon = 126.9780;
        double radius = 500; // meters
        Category category = Category.MT1;

        String expectedCategory = URLEncoder.encode(category.toString(), StandardCharsets.UTF_8.name());

        // When
        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(lat, lon, radius, category);

        // Then
        String expectedUri = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=" +
                expectedCategory + "&x=" + lon + "&y=" + lat + "&radius=" + radius + "&sort=distance";

        assertEquals(expectedUri, uri.toString());
    }



}