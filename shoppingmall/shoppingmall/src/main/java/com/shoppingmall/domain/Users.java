package com.shoppingmall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Users {

    @Id
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;
    @Column(name = "place")
    private String place;
    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "role")
    private String role;

    @Column(name = "enabled")
    private String enabled;

    @Column(name = "email")
    private String email;

    @Column(name = "sex")
    private String sex;

    public Users() {

    }
}
