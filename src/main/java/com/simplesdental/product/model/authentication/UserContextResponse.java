package com.simplesdental.product.model.authentication;

import com.simplesdental.product.Utils.Role;

public record UserContextResponse(Long id, String email, Role role) {}
