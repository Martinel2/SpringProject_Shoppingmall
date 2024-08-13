package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Purchases {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
