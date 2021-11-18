package com.servustech.skeleton.shop;

import com.servustech.skeleton.features.account.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShopController {

    @GetMapping("/shop")
    public String getCurrentShop(@RequestBody ShopDto shop) {
        System.out.println(shop);
        return "Congratulation Shop you can access this api";
    }
}