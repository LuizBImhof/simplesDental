package com.simplesdental.product.service;

import com.simplesdental.product.model.authentication.PasswordUpdateRequest;
import com.simplesdental.product.model.User;
import com.simplesdental.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updatePassword(User user, PasswordUpdateRequest request) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
