package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Products {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id")
    private Category category;

    // Foreign key field
    private String seller_id;

    @ManyToOne
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Users user;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private int price;

    @Lob  // 대용량 데이터를 저장할 때 사용

    @Column(name = "description")
    private String description;  // 상품 설명
    @Column(name = "photo")
    private String photo;  // 사진 파일 경로 또는 URL


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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
}
