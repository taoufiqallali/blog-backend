package com.formation.blogapp.service.impl;


import com.formation.blogapp.domain.BlogPost;
import com.formation.blogapp.domain.Comment;
import com.formation.blogapp.domain.User;
import com.formation.blogapp.dto.Response.CommentResponse;
import com.formation.blogapp.dto.request.CreateCommentRequest;
import com.formation.blogapp.exception.BlogPostNotFoundException;
import com.formation.blogapp.exception.CommentNotFoundException;
import com.formation.blogapp.exception.UserNotFoundException;
import com.formation.blogapp.mapper.CommentMapper;
import com.formation.blogapp.repository.BlogPostRepository;
import com.formation.blogapp.repository.CommentRepository;
import com.formation.blogapp.repository.UserRepository;
import com.formation.blogapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    public final CommentRepository commentRepository;
    public final BlogPostRepository blogPostRepository;
    public final UserRepository userRepository;
    public final CommentMapper mapper;

    @Override
    public CommentResponse create(CreateCommentRequest request) {

        BlogPost post = blogPostRepository
                .findById(request.blogId())
                .orElseThrow(() -> new BlogPostNotFoundException(request.blogId()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        Comment comment = new Comment();
        comment.setBlog(post);
        comment.setAuthor(user);
        comment.setContent(request.content());

        return mapper.toDto(commentRepository.save(comment));

    }

    @Override
    public Page<CommentResponse> getCommentsByBlog(Long blogId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByBlogId(blogId, pageable);
        return comments.map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !comment.getAuthor().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to delete this comment");
        }

        commentRepository.delete(comment);
    }

}
