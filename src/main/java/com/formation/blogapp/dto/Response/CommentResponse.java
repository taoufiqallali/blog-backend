package com.formation.blogapp.dto.Response;

import java.time.Instant;
import java.time.LocalDateTime;

public record CommentResponse(

        Long id,
        String content,
        String authorName,
        Long blogId,
        LocalDateTime createdAt
) {

}
