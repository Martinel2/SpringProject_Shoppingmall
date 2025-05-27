package com.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Consumer {
    @Id
    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "sex")
    private String sex;

}
