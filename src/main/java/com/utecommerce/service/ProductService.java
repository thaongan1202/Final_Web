package com.utecommerce.service;

import com.utecommerce.dto.product.ProductDTO;
import com.utecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getFeaturedProducts();
    Page<ProductDTO> getAllProducts(Pageable pageable);
    Page<ProductDTO> searchProducts(String keyword, Pageable pageable);
    Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable);
    Optional<ProductDTO> getProductBySlug(String slug);
    Product save(Product product);
}

