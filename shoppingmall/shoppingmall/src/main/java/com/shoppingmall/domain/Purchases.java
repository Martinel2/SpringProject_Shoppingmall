package com.shoppingmall.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Purchases {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    private int product_id;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Products products;

    @Column(name = "purchase_type")
    private String purchase_type;

    @Column(name = "product_cnt")
    private int product_cnt;

    @Column(name = "use_coupon")
    private int use_coupon; //쿠폰 퍼센트나 감면된 금액 저장

    @Column(name = "price")
    private int price;

    @Column(name = "created")
    private LocalDateTime created;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public String getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(String purchase_type) {
        this.purchase_type = purchase_type;
    }

    public int getProduct_cnt() {
        return product_cnt;
    }

    public void setProduct_cnt(int product_cnt) {
        this.product_cnt = product_cnt;
    }

    public int getUse_coupon() {
        return use_coupon;
    }

    public void setUse_coupon(int use_coupon) {
        this.use_coupon = use_coupon;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
