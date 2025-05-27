package com.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CouponList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String own_id;

    @ManyToOne
    @JoinColumn(name = "own_id", insertable = false, updatable = false)
    private Users user;

    private int coupon_id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;

}
