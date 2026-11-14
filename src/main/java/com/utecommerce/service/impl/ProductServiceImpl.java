package com.utecommerce.service.impl;

import com.utecommerce.dto.product.ProductDTO;
import com.utecommerce.dto.product.ProductVariantDTO;
import com.utecommerce.entity.Product;
import com.utecommerce.entity.ProductImage;
import com.utecommerce.repository.ProductRepository;
import com.utecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrueAndStatus(Product.ProductStatus.ACTIVE).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findByStatus(Product.ProductStatus.ACTIVE, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndStatus(categoryId, Product.ProductStatus.ACTIVE, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductBySlug(String slug) {
        return productRepository.findBySlug(slug).map(this::mapToDTO);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    private ProductDTO mapToDTO(Product product) {
        String primaryImg = null;
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            primaryImg = product.getImages().stream()
                    .filter(ProductImage::getIsPrimary)
                    .map(ProductImage::getSecureUrl)
                    .findFirst()
                    .orElse(product.getImages().get(0).getSecureUrl());
        }

        List<ProductVariantDTO> variants = null;
        if (product.getVariants() != null) {
            variants = product.getVariants().stream()
                    .map(v -> ProductVariantDTO.builder()
                            .id(v.getId())
                            .sku(v.getSku())
                            .size(v.getSize())
                            .color(v.getColor())
                            .price(v.getPrice())
                            .stockQuantity(v.getStockQuantity())
                            .active(v.getActive())
                            .build())
                    .collect(Collectors.toList());
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .description(product.getDescription())
                .basePrice(product.getBasePrice())
                .originalPrice(product.getOriginalPrice())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .primaryImageUrl(primaryImg)
                .isFeatured(product.getIsFeatured())
                .status(product.getStatus().name())
                .variants(variants)
                .build();
    }
}

