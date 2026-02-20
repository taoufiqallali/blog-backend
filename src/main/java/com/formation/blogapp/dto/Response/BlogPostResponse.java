package com.formation.blogapp.dto.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BlogPostResponse(

        Long id,
        String title,
        String slug,
        String content,
        LocalDateTime createdAt

) {
}
