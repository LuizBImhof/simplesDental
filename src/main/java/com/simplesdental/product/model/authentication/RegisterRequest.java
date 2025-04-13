package com.simplesdental.product.model.authentication;

import com.simplesdental.product.Utils.Role;
import jakarta.validation.constraints.Email;

public record RegisterRequest(
        String name,
        @Email
        String email,
        String password,
        Role role
) {}
