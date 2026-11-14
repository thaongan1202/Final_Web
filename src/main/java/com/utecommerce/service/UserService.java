package com.utecommerce.service;

import com.utecommerce.dto.auth.RegisterRequest;
import com.utecommerce.entity.User;

import java.util.Optional;

public interface UserService {
    User register(RegisterRequest request);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

