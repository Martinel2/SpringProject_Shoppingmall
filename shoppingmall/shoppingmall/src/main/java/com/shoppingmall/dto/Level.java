package com.shoppingmall.dto;

public enum Level {
    BASIC(1), NORMAL(2), VIP(3), VVIP(4); // enum 오브젝트 정의

    private final int value;

    Level(int value) { // 생성자 만들기
        this.value = value;
    }

    public int intValue() { // 값을 가져오는 메소드
        return value;
    }
}
