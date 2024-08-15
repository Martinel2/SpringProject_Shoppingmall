package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    private int product_id;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Products product;

    @Column(name = "count")
    private int count;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
