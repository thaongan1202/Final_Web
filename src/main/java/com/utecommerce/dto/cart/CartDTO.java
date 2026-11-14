package com.utecommerce.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    @Builder.Default
    private List<CartItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private Integer totalQuantity;
}

