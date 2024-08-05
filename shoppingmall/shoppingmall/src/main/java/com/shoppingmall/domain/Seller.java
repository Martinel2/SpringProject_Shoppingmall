package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
