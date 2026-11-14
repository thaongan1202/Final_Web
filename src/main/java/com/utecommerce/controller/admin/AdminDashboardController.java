package com.utecommerce.controller.admin;

import com.utecommerce.entity.OrderStatus;
import com.utecommerce.repository.OrderRepository;
import com.utecommerce.repository.ProductRepository;
import com.utecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productRepository.count());
        model.addAttribute("totalOrders", orderRepository.count());
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("pendingOrders", orderRepository.countByStatus(OrderStatus.PENDING_CONFIRMATION));
        model.addAttribute("pageTitle", "Bảng điều khiển Quản trị - UTECommerce");
        return "admin/dashboard";
    }
}

