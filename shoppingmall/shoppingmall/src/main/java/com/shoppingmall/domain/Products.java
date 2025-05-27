package com.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    public double getAverageRating() {
        return ratingCnt > 0 ? (double) ratingSum / ratingCnt : 0;
    }
}
