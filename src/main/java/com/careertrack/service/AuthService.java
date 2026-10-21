package com.careertrack.service;

import com.careertrack.dto.LoginRequest;
import com.careertrack.dto.RegisterRequest;
import com.careertrack.dto.TokenResponse;
import com.careertrack.dto.UserResponse;
import com.careertrack.entity.User;
import com.careertrack.exception.EmailAlreadyUsedException;
import com.careertrack.repository.UserRepository;
import com.careertrack.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public UserResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException(email);
        }

        String passwordHash = passwordEncoder.encode(request.password());
        User user = new User(email, request.name().trim(), passwordHash);
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        String email = normalizeEmail(request.email());

        // Controlla email e password: se sono sbagliate lancia un'eccezione
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(email, request.password()));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenziali non valide"));

        String token = tokenService.generateToken(user);
        return new TokenResponse(token, "Bearer", tokenService.getExpirationSeconds());
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}