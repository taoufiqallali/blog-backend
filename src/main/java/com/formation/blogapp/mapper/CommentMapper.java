package com.formation.blogapp.mapper;

import com.formation.blogapp.domain.Comment;
import com.formation.blogapp.dto.Response.CommentResponse;
import com.formation.blogapp.dto.request.CreateCommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "authorName", source = "author.firstname")
    @Mapping(target = "blogId", source = "blog.id")
    public CommentResponse toDto(Comment comment);


}
