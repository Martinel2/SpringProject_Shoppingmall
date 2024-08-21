package com.shoppingmall.dto;

import com.shoppingmall.domain.Coupon;

import java.time.LocalDate;

public class CouponDto {
    Coupon coupon;

    LocalDate expired;

    public CouponDto(Coupon coupon, LocalDate expired) {
        this.coupon = coupon;
        this.expired = expired;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public LocalDate getExpired() {
        return expired;
    }

    public void setExpired(LocalDate expired) {
        this.expired = expired;
    }
}
