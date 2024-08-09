package com.shoppingmall.domain;


import jakarta.persistence.*;

@Entity
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getOwn_id() {
        return own_id;
    }

    public void setOwn_id(String own_id) {
        this.own_id = own_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }
}
