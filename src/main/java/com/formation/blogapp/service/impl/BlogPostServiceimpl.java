package com.formation.blogapp.service.impl;

import com.formation.blogapp.domain.BlogPost;
import com.formation.blogapp.domain.User;
import com.formation.blogapp.dto.Response.BlogPostResponse;
import com.formation.blogapp.dto.request.CreateBlogPostRequest;
import com.formation.blogapp.dto.request.UpdateBlogRequest;
import com.formation.blogapp.exception.BlogPostNotFoundException;
import com.formation.blogapp.exception.UserNotFoundException;
import com.formation.blogapp.mapper.BlogPostMapper;
import com.formation.blogapp.repository.BlogPostRepository;
import com.formation.blogapp.repository.UserRepository;
import com.formation.blogapp.service.BlogPostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BlogPostServiceimpl implements BlogPostService {

        private final BlogPostRepository blogPostRepository;
        private final UserRepository userRepository;
        private final BlogPostMapper mapper;


        @Transactional
        @Override
        public BlogPostResponse create(CreateBlogPostRequest request) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();
            User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException(userEmail));

            BlogPost blogPost = mapper.toEntity(request);
            blogPost.setAuthor(user);
            blogPost.setSlug(createUniqueSlug(request.title()));

            return mapper.toDto(blogPostRepository.save(blogPost));
        }

    @Override
    public BlogPostResponse getPostById(Long id) {
        return mapper.toDto(blogPostRepository.findById(id).orElseThrow(() -> new BlogPostNotFoundException(id)));
    }

    public Page<BlogPostResponse> getPosts(Pageable pageable){

        Page<BlogPost> posts = blogPostRepository.findAll(pageable);

        return posts.map(mapper::toDto);
    }

    @Override
    public Page<BlogPostResponse> searchPosts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return getPosts(pageable);
        }
        return blogPostRepository.searchPosts(keyword, pageable).map(mapper::toDto);
    }



    @Override
    public void deletePost(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        BlogPost post;

        if (isAdmin) {
            post = blogPostRepository.findById(id)
                    .orElseThrow(() -> new BlogPostNotFoundException(id));
        } else {
            post = blogPostRepository.findByIdAndAuthorEmail(id, email)
                    .orElseThrow(() -> new AccessDeniedException("Not owner"));
        }

        blogPostRepository.delete(post);
    }

    @Override
    public Page<BlogPostResponse> getMyPosts(Pageable pageable) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return blogPostRepository.findByAuthorEmail(email, pageable).map(mapper::toDto);
    }


    @Transactional
    @Override
    public BlogPostResponse updateBlog(UpdateBlogRequest request) {

            BlogPost blog = blogPostRepository
                    .findById(request.id())
                    .orElseThrow(() -> new BlogPostNotFoundException(request.id()));

            blog.setTitle(request.title());
            blog.setContent(request.content());

        return mapper.toDto(blog);
    }

    public String createUniqueSlug(String title) {

            String baseSlug = slugify(title);
            String slug = baseSlug;
            int counter = 1;

            while (blogPostRepository.existsBySlug(slug)) {
                slug = baseSlug + "-" + counter++;
            }

            return slug;
        }

        public String slugify(String input){
            return input.toLowerCase()
                    .replaceAll("[^a-z0-9\\s-]", "")
                    .trim()
                    .replaceAll("\\s+", "-");
        }

}
