package com.simplesdental.product.model.authentication;

import com.simplesdental.product.Utils.Role;

public record RegisterRequest(
        String name,
        String email,
        String password,
        Role role
) {}
