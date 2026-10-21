package com.careertrack.controller;

import com.careertrack.dto.LoginRequest;
import com.careertrack.dto.RegisterRequest;
import com.careertrack.dto.TokenResponse;
import com.careertrack.dto.UserResponse;
import com.careertrack.security.CurrentUserId;
import com.careertrack.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register -> crea un nuovo utente
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    // POST /api/auth/login -> restituisce un token JWT
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // GET /api/auth/me -> i dati dell'utente a cui appartiene il token
    @GetMapping("/me")
    public UserResponse me(@CurrentUserId Long userId) {
        return authService.getCurrentUser(userId);
    }
}