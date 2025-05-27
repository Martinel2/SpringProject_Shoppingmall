package com.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
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

}
