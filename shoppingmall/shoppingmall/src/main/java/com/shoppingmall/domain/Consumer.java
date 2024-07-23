package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Consumer {
    @Id
    @OneToOne
    @JoinColumn(name = "userId")
    private Users userId;

    @Column(name = "birth")
    private String birth;

    @Column(name = "sex")
    private String sex;

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
