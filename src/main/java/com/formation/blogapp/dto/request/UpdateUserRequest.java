package com.formation.blogapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank
        @Size(min = 3, max = 20)
                String firstname,

        @NotBlank
        @Size(min = 3, max = 20)
        String lastname,

        @Email
        @NotBlank
        @Size(max = 255)
        String email
) {
}
