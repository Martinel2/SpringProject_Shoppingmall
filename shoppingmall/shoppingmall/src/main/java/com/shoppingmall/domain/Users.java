package com.shoppingmall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
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
    private String birth;

    @Column(name = "role")
    private String role;

    @Column(name = "enabled")
    private String enabled;

    @Column(name = "email")
    private String email;


    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private Users(String id, String password, String name, String place, String phone, String birth, String email, String enabled, String role){
        this.id = id;
        this.password = password;
        this.name = name;
        this.place = place;
        this.phone = phone;
        this.birth = birth;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    /*public static Users createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        return new Users(userId, passwordEncoder.encode(pw), null, null, null, null, null, null,"USER");
    }
     */
    public void setId(String id) { this.id = id;}
    @Id
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birthday) {
        this.birth = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Users(){}


}
