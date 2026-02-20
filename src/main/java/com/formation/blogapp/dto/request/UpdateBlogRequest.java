package com.formation.blogapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBlogRequest(

        Long id,

        @NotBlank
        @Size(min = 3, max = 255)
        String title,

        @NotBlank
        String content

) {
}
