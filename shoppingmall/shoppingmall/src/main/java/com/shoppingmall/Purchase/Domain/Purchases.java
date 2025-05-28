package com.shoppingmall.Purchase.Domain;

import com.shoppingmall.Coupon.Domain.Coupon;
import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.User.Domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "coupon")
    private Coupon coupon; //쿠폰 퍼센트나 감면된 금액 저장

    @Column(name = "price")
    private int price;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "order_id")
    private String order_id;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }

}
