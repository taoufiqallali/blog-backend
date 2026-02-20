package com.formation.blogapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBlogPostRequest(

        @NotBlank
        @Size(min = 3, max = 255)
        String title,

        @NotBlank
        String content
) {
}
