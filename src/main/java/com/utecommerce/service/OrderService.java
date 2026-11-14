package com.utecommerce.service;

import com.utecommerce.dto.order.CheckoutRequest;
import com.utecommerce.entity.Order;
import com.utecommerce.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId, CheckoutRequest request);
    Order getOrderByCode(String orderCode);
    List<Order> getOrdersByUserId(Long userId);
    Page<Order> getAllOrders(OrderStatus status, Pageable pageable);
    Order updateOrderStatus(Long orderId, OrderStatus newStatus, Long actorUserId, String note);
    void cancelOrder(Long orderId, Long userId, String reason);
}

