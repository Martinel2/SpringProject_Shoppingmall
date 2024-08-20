package com.shoppingmall.domain;

import jakarta.persistence.*;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(int validTerm) {
        this.validTerm = validTerm;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
