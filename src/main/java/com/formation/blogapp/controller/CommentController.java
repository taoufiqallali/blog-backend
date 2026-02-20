package com.formation.blogapp.controller;


import com.formation.blogapp.dto.Response.CommentResponse;
import com.formation.blogapp.dto.request.CreateCommentRequest;
import com.formation.blogapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    public final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CreateCommentRequest request){
        CommentResponse commentResponse = commentService.create(request);
        URI uri = URI.create("/api/v1/comment/" + commentResponse.id());
        return ResponseEntity.created(uri).body(commentResponse);
    }


    @GetMapping("/{blogId}")
    public ResponseEntity<Page<CommentResponse>> getCommentsByBlog(@PathVariable Long blogId, Pageable pageable){
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId, pageable));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
