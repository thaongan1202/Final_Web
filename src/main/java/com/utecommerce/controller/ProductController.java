package com.utecommerce.controller;

import com.utecommerce.dto.product.CategoryDTO;
import com.utecommerce.dto.product.ProductDTO;
import com.utecommerce.service.CategoryService;
import com.utecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String listProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sortParam,
            Model model
    ) {
        String[] sortParts = sortParam.split(",");
        String sortProperty = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        Page<ProductDTO> productPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            productPage = productService.searchProducts(keyword.trim(), pageable);
            model.addAttribute("keyword", keyword);
        } else if (categoryId != null) {
            productPage = productService.getProductsByCategory(categoryId, pageable);
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            productPage = productService.getAllProducts(pageable);
        }

        List<CategoryDTO> categories = categoryService.getAllActiveCategories();

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Danh sách sản phẩm - UTECommerce");

        return "client/products";
    }

    @GetMapping("/{slug}")
    public String productDetail(@PathVariable String slug, Model model) {
        return productService.getProductBySlug(slug)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("pageTitle", product.getName() + " - UTECommerce");
                    return "client/product-detail";
                })
                .orElse("redirect:/products");
    }
}

