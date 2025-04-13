package com.simplesdental.product.controller;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.authentication.AuthRequest;
import com.simplesdental.product.model.authentication.AuthResponse;
import com.simplesdental.product.model.authentication.RegisterRequest;
import com.simplesdental.product.model.authentication.UserContextResponse;
import com.simplesdental.product.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/context")
    public ResponseEntity<UserContextResponse> context(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.context(user));
    }
}
