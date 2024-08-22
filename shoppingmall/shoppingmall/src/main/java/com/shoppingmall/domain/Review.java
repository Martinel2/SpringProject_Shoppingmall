package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private Users users;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false)
    private Products products;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private int rating;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchases purchases;

    @Lob
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Lob
    @Column(name = "photo")
    private String photo;  // 사진 파일 경로 또는 URL

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Purchases getPurchases() {
        return purchases;
    }

    public void setPurchases(Purchases purchases) {
        this.purchases = purchases;
    }
}
