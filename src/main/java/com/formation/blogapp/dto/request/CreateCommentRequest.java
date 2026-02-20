package com.formation.blogapp.dto.request;

import jakarta.validation.constraints.Size;

public record CreateCommentRequest(

        @Size(min = 3, max = 255)
        String content,
        Long blogId

) {
}
