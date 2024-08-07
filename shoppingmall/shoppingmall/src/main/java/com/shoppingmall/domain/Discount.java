package com.shoppingmall.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Discount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private int product_id;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    private Products products;


    @Column(name = "percent")
    private int percent;

    @Column(name = "term")
    private Date term;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }
}
