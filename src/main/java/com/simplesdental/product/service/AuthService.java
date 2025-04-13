package com.simplesdental.product.service;

import com.simplesdental.product.config.JwtUtil;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.authentication.AuthRequest;
import com.simplesdental.product.model.authentication.AuthResponse;
import com.simplesdental.product.model.authentication.RegisterRequest;
import com.simplesdental.product.model.authentication.UserContextResponse;
import com.simplesdental.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("E-mail já registrado");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        return userRepository.save(user);
    }

    public UserContextResponse context(User authenticatedUser) {
        return new UserContextResponse(authenticatedUser.getId(), authenticatedUser.getEmail(), authenticatedUser.getRole());
    }
}
