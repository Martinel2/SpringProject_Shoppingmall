package com.shoppingmall.Wishlist.Domain;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.User.Domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

}
