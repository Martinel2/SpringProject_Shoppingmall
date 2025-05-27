package com.shoppingmall.Coupon.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valid_term")
    private int validTerm;

    @Column(name = "percent")
    private int percent;

    @Column(name = "level")
    private int level;

}
