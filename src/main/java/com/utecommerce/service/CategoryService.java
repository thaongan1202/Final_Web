package com.utecommerce.service;

import com.utecommerce.dto.product.CategoryDTO;
import com.utecommerce.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllActiveCategories();
    Optional<Category> findBySlug(String slug);
    Category save(Category category);
}

