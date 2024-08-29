package com.shoppingmall.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Consumer {
    @Id
    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "sex")
    private String sex;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
