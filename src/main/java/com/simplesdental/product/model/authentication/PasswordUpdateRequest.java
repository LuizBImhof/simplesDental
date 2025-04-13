package com.simplesdental.product.model.authentication;

public record PasswordUpdateRequest(String currentPassword, String newPassword) {}
