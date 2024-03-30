package com.shoppingmall.domain;

public class User {

    private long num;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    private String id;
    private String pw;

    private String name;
    private String place;
    private Day birthday;

    Level level; //등급
    private int total; // 총 구매액

    public void setId(String id) { this.id = id;}
    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Day getBirthday() {
        return birthday;
    }

    public void setBirthday(Day birthday) {
        this.birthday = birthday;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public User(){}


}
