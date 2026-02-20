package com.formation.blogapp.mapper;

import com.formation.blogapp.domain.BlogPost;
import com.formation.blogapp.dto.Response.BlogPostResponse;
import com.formation.blogapp.dto.request.CreateBlogPostRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogPostMapper {


    public BlogPostResponse toDto(BlogPost blogPost);


    public BlogPost toEntity(CreateBlogPostRequest createBlogPostRequest);

}
