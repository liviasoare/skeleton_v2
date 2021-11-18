package com.servustech.skeleton.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class ShopDto {
    private String name;
    private String email;
    private String address;
    private Long userId;
}
