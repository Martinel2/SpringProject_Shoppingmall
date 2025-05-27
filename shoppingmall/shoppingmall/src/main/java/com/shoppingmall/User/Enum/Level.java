package com.shoppingmall.User.Enum;

public enum Level {
    BASIC(1), NORMAL(2), VIP(3), VVIP(4), admin(999); // enum 오브젝트 정의

    private final int value;

    Level(int value) { // 생성자 만들기
        this.value = value;
    }

    public int getValue() { // 값을 가져오는 메소드
        return value;
    }

    // 문자열을 받아 해당하는 enum을 반환하는 메서드
    public static Level fromString(String status) {
        for (Level s : Level.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    // 문자열을 받아 정수값을 반환하는 메서드
    public static int toInt(String level) {
        return fromString(level).getValue();
    }
}
