package com.formation.blogapp.controller;


import com.formation.blogapp.dto.Response.AuthResponse;
import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.ChangePasswordRequest;
import com.formation.blogapp.dto.request.LoginRequest;
import com.formation.blogapp.dto.request.RegisterRequest;
import com.formation.blogapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        UserResponse response = authService.register(request);
        URI uri = URI.create("/api/v1/users" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }


}
