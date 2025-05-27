package com.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seller {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "id")
    private Users user;

    @Column(name = "company_id")
    private String company_id;

    @Column(name = "company_name")
    private String company_name;

}
