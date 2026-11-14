package com.utecommerce.controller;

import com.utecommerce.dto.auth.RegisterRequest;
import com.utecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("errorMessage", "Email hoặc mật khẩu không chính xác!");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Đã đăng xuất thành công!");
        }
        model.addAttribute("pageTitle", "Đăng nhập - UTECommerce");
        return "client/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Đăng ký tài khoản - UTECommerce");
        model.addAttribute("registerRequest", new RegisterRequest());
        return "client/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @Valid @ModelAttribute("registerRequest") RegisterRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Đăng ký tài khoản - UTECommerce");
            return "client/register";
        }

        try {
            userService.register(request);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Đăng ký tài khoản - UTECommerce");
            return "client/register";
        } catch (Exception e) {
            log.error("Lỗi khi đăng ký tài khoản", e);
            model.addAttribute("errorMessage", "Đã xảy ra lỗi, vui lòng thử lại sau!");
            model.addAttribute("pageTitle", "Đăng ký tài khoản - UTECommerce");
            return "client/register";
        }
    }
}
