package com.formation.blogapp.service;

import com.formation.blogapp.dto.Response.BlogPostResponse;
import com.formation.blogapp.dto.request.CreateBlogPostRequest;
import com.formation.blogapp.dto.request.UpdateBlogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BlogPostService {


    BlogPostResponse create(CreateBlogPostRequest request);

    BlogPostResponse getPostById(Long id);

    Page<BlogPostResponse> getPosts(Pageable pageable);

    Page<BlogPostResponse> searchPosts(String keyword, Pageable pageable);

    void deletePost(Long id);

    Page<BlogPostResponse> getMyPosts(Pageable pageable);

    BlogPostResponse updateBlog(UpdateBlogRequest request);
}