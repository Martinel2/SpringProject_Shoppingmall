package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Products {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int category;
    @ManyToOne
    @JoinColumn(name = "category", insertable = false, updatable = false)
    private Category cat;

    // Foreign key field
    private String seller_id;

    @ManyToOne
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Users user;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private int price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "rating_sum")
    private int ratingSum;

    @Column(name = "rating_cnt")
    private int ratingCnt;

    @Lob  // 대용량 데이터를 저장할 때 사용

    @Column(name = "description")
    private String description;  // 상품 설명
    @Column(name = "photo")
    private String photo;  // 사진 파일 경로 또는 URL

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getRatingCnt() {
        return ratingCnt;
    }

    public void setRatingCnt(int ratingCnt) {
        this.ratingCnt = ratingCnt;
    }

    public double getAverageRating() {
        return ratingCnt > 0 ? ratingSum / ratingCnt : 0;
    }
}
