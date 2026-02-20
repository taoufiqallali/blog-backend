package com.formation.blogapp.controller;


import com.formation.blogapp.dto.Response.BlogPostResponse;
import com.formation.blogapp.dto.request.CreateBlogPostRequest;
import com.formation.blogapp.dto.request.UpdateBlogRequest;
import com.formation.blogapp.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/blogPost")
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<BlogPostResponse> createBlogPost(@Valid @RequestBody CreateBlogPostRequest request) {

        BlogPostResponse blogPostResponse = blogPostService.create(request);
        URI uri = URI.create("/api/v1/blogPost/" + blogPostResponse.id());

        return ResponseEntity.created(uri).body(blogPostResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponse> getBlogPost(@PathVariable Long id){
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id){
        blogPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<BlogPostResponse>> getBlogPosts(Pageable pageable){

        return ResponseEntity.ok(
                blogPostService.getPosts(pageable)
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
    public ResponseEntity<Page<BlogPostResponse>> getMyPosts(Pageable pageable){

        return ResponseEntity.ok(
                blogPostService.getMyPosts(pageable)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BlogPostResponse>> search(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        return ResponseEntity.ok(blogPostService.searchPosts(keyword, pageable));
    }

    @PutMapping
    public ResponseEntity<BlogPostResponse> updateBlog(@Valid @RequestBody UpdateBlogRequest request){
        return ResponseEntity.ok(blogPostService.updateBlog(request));
    }


}
