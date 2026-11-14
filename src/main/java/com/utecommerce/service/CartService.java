package com.utecommerce.service;

import com.utecommerce.dto.cart.CartDTO;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addToCart(Long userId, Long variantId, Integer quantity);
    CartDTO updateQuantity(Long userId, Long cartItemId, Integer quantity);
    void removeFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
}

