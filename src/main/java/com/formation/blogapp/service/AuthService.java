package com.formation.blogapp.service;

import com.formation.blogapp.dto.Response.AuthResponse;
import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.ChangePasswordRequest;
import com.formation.blogapp.dto.request.LoginRequest;
import com.formation.blogapp.dto.request.RegisterRequest;

public interface AuthService {

    public UserResponse register(RegisterRequest registerRequest);

    public AuthResponse login(LoginRequest request);

    public void changePassword(ChangePasswordRequest request);

}
