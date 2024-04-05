package com.rko.nearbyplace.service;

import com.rko.nearbyplace.dto.*;
import com.rko.nearbyplace.enumeration.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final ShortUrlService shortUrlService;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private static final int MAX_SEARCH_CNT = 5; // 편의점 최대 검색 갯수
    private static final double RADIUS_KM = 10000; // 반경 10km

    public List<OutputDto> recommendPlaceList(InputDto inputDto){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(inputDto.address());
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.documentList())) {
            log.error("input address : {}", inputDto.address());
            return Collections.emptyList();
        }
        DocumentDto documentDto = kakaoApiResponseDto.documentList().get(0);
        List<DirectionDto> directionDtoList = buildDirectionListByCategoryApi(documentDto, inputDto.category());

        return directionDtoList
                .stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    public OutputDto toOutputDto(DirectionDto dto){
        String params = String.join(",", dto.targetPlaceName(),
                String.valueOf(dto.targetLatitude()),
                String.valueOf(dto.targetLongitude()));
        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();
       String roadViewUrl = ROAD_VIEW_BASE_URL + dto.targetLatitude() + "," + dto.targetLongitude();

       String shortPlaceUrl = shortUrlService.shortenUrl(result);
       String shortRoadViewUrl = shortUrlService.shortenUrl(roadViewUrl);

        return OutputDto.builder()
                .placeName(dto.targetPlaceName())
                .placeAddress(dto.targetAddress())
                .placeUrl(shortPlaceUrl)
                .roadViewUrl(shortRoadViewUrl)
                .distance(String.format(dto.distance() + "m"))
                .build();
    }


    public List<DirectionDto> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto, Category category) {
        if (Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoAddressSearchService
                .requestCategorySearch(inputDocumentDto.latitude(), inputDocumentDto.longitude(), RADIUS_KM, category)
                .documentList()
                .stream().map(resultDocumentDto ->
                        DirectionDto.builder()
                                .inputAddress(inputDocumentDto.addressName())
                                .inputLatitude(inputDocumentDto.latitude())
                                .inputLongitude(inputDocumentDto.longitude())
                                .targetPlaceName(resultDocumentDto.placeName())
                                .targetAddress(resultDocumentDto.addressName())
                                .targetLatitude(resultDocumentDto.latitude())
                                .targetLongitude(resultDocumentDto.longitude())
                                .distance(resultDocumentDto.distance())
                                .build())
                .limit(MAX_SEARCH_CNT)
                .collect(Collectors.toList());
    }
}
