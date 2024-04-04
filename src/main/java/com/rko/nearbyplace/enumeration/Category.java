package com.rko.nearbyplace.enumeration;

import lombok.Getter;

@Getter
public enum Category {
    MT1("대형마트"),
    CS2("편의점"),
    PK6("주차장"),
    OL7("주유소, 충전소"),
    SW8("지하철역"),
    BK9("은행"),
    CT1("문화시설"),
    AG2("중개업소"),
    PO3("공공기관"),
    AT4("관광명소"),
    AD5("숙박"),
    FD6("음식점"),
    CE7("카페"),
    HP8("병원"),
    PM9("약국");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
