package com.formation.blogapp.service;


import com.formation.blogapp.dto.Response.CommentResponse;
import com.formation.blogapp.dto.request.CreateCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentResponse create(CreateCommentRequest request);

    Page<CommentResponse> getCommentsByBlog(Long blogId, Pageable pageable);

    void delete(Long id);


}
