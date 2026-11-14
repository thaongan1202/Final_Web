package com.utecommerce.entity;

public enum OrderStatus {
    PENDING_CONFIRMATION, // Chờ xác nhận
    CONFIRMED,            // Đã xác nhận
    PROCESSING,           // Đang xử lý đóng gói
    SHIPPED,              // Đang giao hàng
    DELIVERED,            // Đã giao hàng thành công
    CANCELLED             // Đã hủy
}

