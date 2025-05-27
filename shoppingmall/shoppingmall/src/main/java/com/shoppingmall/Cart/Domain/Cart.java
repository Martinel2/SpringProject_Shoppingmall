package com.shoppingmall.Cart.Domain;

import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Product.Domain.Products;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    private int quantity;
}
