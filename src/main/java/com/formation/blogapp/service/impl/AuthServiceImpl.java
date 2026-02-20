package com.formation.blogapp.service.impl;

import com.formation.blogapp.exception.EmailAlreadyExistsException;
import com.formation.blogapp.exception.InvalidCredentialsException;
import com.formation.blogapp.exception.PasswordSameAsOldException;
import com.formation.blogapp.exception.UserNotFoundException;
import com.formation.blogapp.domain.User;
import com.formation.blogapp.dto.Response.AuthResponse;
import com.formation.blogapp.dto.Response.UserResponse;
import com.formation.blogapp.dto.request.ChangePasswordRequest;
import com.formation.blogapp.dto.request.LoginRequest;
import com.formation.blogapp.dto.request.RegisterRequest;
import com.formation.blogapp.mapper.UserMapper;
import com.formation.blogapp.repository.UserRepository;
import com.formation.blogapp.security.JwtService;
import com.formation.blogapp.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    @Override
    public UserResponse register(RegisterRequest registerRequest) {

        User user = mapper.toEntity(registerRequest);

        if(userRepository.existsByEmail(mapper.toEntity(registerRequest).getEmail())){
            throw new EmailAlreadyExistsException(registerRequest.email());
        }

        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        return  mapper.toDto(userRepository.save(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );



        String email = authentication.getName();


        String token = jwtService.generateToken(email);
        return new AuthResponse(token);
    }



    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("invalid password");
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPasswordHash())) {
            throw new PasswordSameAsOldException();
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
    }

}
