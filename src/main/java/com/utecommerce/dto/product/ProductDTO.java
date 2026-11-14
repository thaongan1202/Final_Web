package com.utecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String description;
    private BigDecimal basePrice;
    private BigDecimal originalPrice;
    private String categoryName;
    private Long categoryId;
    private String brandName;
    private String primaryImageUrl;
    private Boolean isFeatured;
    private String status;
    private List<ProductVariantDTO> variants;
}

