package com.servustech.skeleton.shop;

import com.servustech.skeleton.features.account.User;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;
    private String address;

    @OneToOne(mappedBy = "shop")
    private User user;

    public Shop() {
    }

    public Shop(Long id,
                String name,
                String email,
                String address
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Shop(String name,
                String email,
                String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}