package com.simplesdental.product.controller;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.authentication.PasswordUpdateRequest;
import com.simplesdental.product.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest request,
                                               @AuthenticationPrincipal User user) {
        userService.updatePassword(user, request);
        return ResponseEntity.noContent().build();
    }
}
