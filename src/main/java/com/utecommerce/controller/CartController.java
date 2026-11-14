package com.utecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("pageTitle", "Giỏ hàng - UTECommerce");
        return "client/cart";
    }
}

