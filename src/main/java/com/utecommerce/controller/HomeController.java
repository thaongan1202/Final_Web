package com.utecommerce.controller;

import com.utecommerce.dto.product.CategoryDTO;
import com.utecommerce.dto.product.ProductDTO;
import com.utecommerce.service.CategoryService;
import com.utecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        List<CategoryDTO> categories = categoryService.getAllActiveCategories();
        List<ProductDTO> featuredProducts = productService.getFeaturedProducts();

        model.addAttribute("categories", categories);
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("pageTitle", "UTECommerce - Hệ Thống Thương Mại Điện Tử Thời Trang");
        return "client/home";
    }
}
