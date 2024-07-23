package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
public class Seller {

    @Id
    @OneToOne
    @JoinColumn(name = "userId")
    private Users userId;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "companyName")
    private String companyName;

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
