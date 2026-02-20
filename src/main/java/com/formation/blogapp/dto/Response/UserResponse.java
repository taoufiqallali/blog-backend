package com.formation.blogapp.dto.Response;

import com.formation.blogapp.enums.Role;

public record UserResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        Role role
) {
}
